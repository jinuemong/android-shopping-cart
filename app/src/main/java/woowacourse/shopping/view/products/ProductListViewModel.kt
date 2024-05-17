package woowacourse.shopping.view.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.view.detail.ProductDetailState

class ProductListViewModel(
    private val repository: ProductRepository,
) : ViewModel() {
    private val _products: MutableLiveData<List<Product>> = MutableLiveData(emptyList())
    val products: LiveData<List<Product>> get() = _products

    private val _productListState: MutableLiveData<ProductListState> =
        MutableLiveData(ProductListState.Init)
    val productListState: LiveData<ProductListState> get() = _productListState

    fun loadPagingProduct(pagingSize: Int) {
        runCatching {
            val itemSize = products.value?.size ?: DEFAULT_ITEM_SIZE
            repository.loadPagingProducts(itemSize, pagingSize)
        }
            .onSuccess {
                _products.value = _products.value?.plus(it)
                _productListState.value = ProductListState.LoadProductList.Success
            }
            .onFailure {
                _productListState.value = ProductListState.LoadProductList.Fail
            }
    }

    companion object {
        private const val DEFAULT_ITEM_SIZE = 0
    }
}
