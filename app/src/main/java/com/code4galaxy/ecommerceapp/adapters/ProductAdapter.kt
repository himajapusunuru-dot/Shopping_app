package com.code4galaxy.ecommerceapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.code4galaxy.ecommerceapp.databinding.ItemProductBinding
import com.code4galaxy.ecommerceapp.model.local.CartItem
import com.code4galaxy.ecommerceapp.response.Product
import com.code4galaxy.ecommerceapp.utils.GenericDiffUtil
import com.code4galaxy.ecommerceapp.viewholders.ProductViewHolder

class ProductAdapter(
    private val onProductClick : (Product) -> Unit,
    private val onAddToCart: (Product) -> Unit,
    private val onIncrease: (CartItem) -> Unit,
    private val onDecrease: (CartItem) -> Unit
): ListAdapter<Product, ProductViewHolder>(GenericDiffUtil<Product>(
    areItemsSame = {oldItem,newItem->
        oldItem.productId == newItem.productId
    }
)) {
    private var cartItems : List<CartItem> = emptyList()
    fun updateCartItems(items:List<CartItem>){
        cartItems = items
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ProductViewHolder(binding, onProductClick, onAddToCart, onIncrease, onDecrease)
    }

    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int
    ) {
        val product = getItem(position)
        val cartItem = cartItems.find { it.productId == product.productId }
        holder.bind(product,cartItem,onAddToCart,onIncrease,onDecrease)
//        holder.bind(getItem(position))
    }
}