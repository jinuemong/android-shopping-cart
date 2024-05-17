package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.Product

interface ProductRepository {
    fun loadPagingProducts(
        offset: Int,
        pagingSize: Int,
    ): List<Product>

    fun getProduct(productId: Long): Product
}
