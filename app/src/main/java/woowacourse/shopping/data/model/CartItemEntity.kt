package woowacourse.shopping.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.shopping.data.db.cartItem.CartItemDatabase.Companion.CART_ITEMS_DB_NAME
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.CartItemCounter
import woowacourse.shopping.domain.model.Product

@Entity(tableName = CART_ITEMS_DB_NAME)
data class CartItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val product: Product,
    val productId: Long = product.id,
    val count: Int = DEFAULT_ITEM_COUNT,
) {
    fun toCartItem(): CartItem {
        return CartItem(
            id = id,
            product = product,
            cartItemCounter = CartItemCounter(count = count)
        )
    }

    companion object {
        private const val DEFAULT_ITEM_COUNT = 1
        fun makeCartItemEntity(product: Product): CartItemEntity {
            return CartItemEntity(product = product)
        }
    }
}
