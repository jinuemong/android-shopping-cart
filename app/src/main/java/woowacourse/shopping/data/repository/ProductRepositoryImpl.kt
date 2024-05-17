package woowacourse.shopping.data.repository

import woowacourse.shopping.data.db.product.ProductDao
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.utils.NoSuchDataException

class ProductRepositoryImpl : ProductRepository {
    private val productDao = ProductDao()

    override fun loadPagingProducts(
        offset: Int,
        pagingSize: Int,
    ): List<Product> {
        return productDao.findPagingProducts(offset, pagingSize)
    }

    override fun getProduct(productId: Long): Product {
        val product = productDao.findProductById(productId)
        return product ?: throw NoSuchDataException()
    }
}
