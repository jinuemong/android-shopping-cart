package woowacourse.shopping.view.products.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.Product
import woowacourse.shopping.view.products.OnClickProduct
import woowacourse.shopping.view.products.adapter.viewholder.ProductViewHolder

class ProductAdapter(
    private val onClickProduct: OnClickProduct,
): RecyclerView.Adapter<ProductViewHolder>() {
    private var products : List<Product> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = ItemProductBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ProductViewHolder(view,onClickProduct)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = products[position]
        holder.bind(item)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateProducts(products: List<Product>){
        this.products = products
        notifyDataSetChanged()
    }
}
