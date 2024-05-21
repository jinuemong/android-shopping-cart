package woowacourse.shopping.view.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.repository.ProductRepositoryImpl.Companion.DEFAULT_ITEM_SIZE
import woowacourse.shopping.domain.model.CartItemCounter
import woowacourse.shopping.domain.model.CartItemResult
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import woowacourse.shopping.utils.MutableSingleLiveData
import woowacourse.shopping.utils.NoSuchDataException
import woowacourse.shopping.utils.SingleLiveData

class ProductListViewModel(
    private val productRepository: ProductRepository,
    private val shoppingCartRepository: ShoppingCartRepository,
) : ViewModel() {
    private val _products: MutableLiveData<List<Product>> = MutableLiveData(emptyList())
    val products: LiveData<List<Product>> get() = _products

    private val _productListEvent: MutableSingleLiveData<ProductListEvent> =
        MutableSingleLiveData()
    val productListEvent: SingleLiveData<ProductListEvent> get() = _productListEvent

    private val _errorState: MutableSingleLiveData<ProductListEvent.ErrorEvent> =
        MutableSingleLiveData()
    val errorState: SingleLiveData<ProductListEvent.ErrorEvent> get() = _errorState

    fun loadPagingProduct() {
        try {
            val itemSize = products.value?.size ?: DEFAULT_ITEM_SIZE
            val pagingData = productRepository.loadPagingProducts(itemSize)
            _products.value = _products.value?.plus(pagingData)
            _productListEvent.postValue(ProductListEvent.LoadProductEvent.Success)
        } catch (e: Exception) {
            when (e) {
                is NoSuchDataException ->
                    _errorState.postValue(
                        ProductListEvent.LoadProductEvent.Fail
                    )

                else -> _errorState.postValue(ProductListEvent.ErrorEvent.NotKnownError)
            }
        }
    }

    fun updateShoppingCart(
        product: Product,
        itemCounter: CartItemCounter,
    ) {
        val cartItemResult =
            shoppingCartRepository.getCartItemResultFromProductId(productId = product.id)
        when (cartItemResult) {
            is CartItemResult.Exists -> {
                shoppingCartRepository.updateCartItem(
                    cartItemResult.cartItemId,
                    itemCounter.itemCount
                )
            }

            CartItemResult.NotExists -> {
                shoppingCartRepository.addCartItem(product)
            }
        }
    }

    fun deleteCartItem(productId: Long) {
        val cartItemResult =
            shoppingCartRepository.getCartItemResultFromProductId(productId = productId)
        when (cartItemResult) {
            is CartItemResult.Exists -> shoppingCartRepository.deleteCartItem(cartItemResult.cartItemId)

            CartItemResult.NotExists -> {
                // view toast NotExists
            }
        }
    }
}
