package com.code4galaxy.ecommerceapp.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.code4galaxy.ecommerceapp.R
import com.code4galaxy.ecommerceapp.databinding.ItemProductBinding
import com.code4galaxy.ecommerceapp.model.local.CartItem
import com.code4galaxy.ecommerceapp.response.Product
import com.code4galaxy.ecommerceapp.utils.hide
import com.code4galaxy.ecommerceapp.utils.show

class ProductViewHolder (
    private val binding: ItemProductBinding,
    private val onProductClick:(Product)-> Unit,
    private val onAddToCart: (Product) -> Unit,
    private val onIncrease: (CartItem) -> Unit,
    private val onDecrease: (CartItem) -> Unit
): RecyclerView.ViewHolder(binding.root){
    fun bind(
        product: Product,
        cartItem: CartItem?,
        onAddToCart: (Product) -> Unit,
        onIncrease: (CartItem) -> Unit,
        onDecrease: (CartItem) -> Unit
    ){
        binding.productName.text = product.productName
        binding.productDescription.text = product.description
        binding.productPrice.text="$${product.price}"
        binding.productRating.rating = product.averageRating.toFloatOrNull() ?:0f
        val imageUrl = "http://gminnovex.com/myshop/images/" + product.productImageUrl.trimStart('/')
        Glide.with(binding.productImage.context)
            .load(imageUrl)
            .error(R.drawable.error)
            .into(binding.productImage)
        binding.root.setOnClickListener {
            onProductClick(product)
        }
        if(cartItem != null){
            binding.addToCart.hide()
            binding.quantityContainer.show()
            binding.tvQuantity.text = cartItem.quantity.toString()
            binding.btnIncrease.setOnClickListener { onIncrease(cartItem) }
            binding.btnDecrease.setOnClickListener { onDecrease(cartItem) }
        }
        else{
            binding.addToCart.show()
            binding.quantityContainer.hide()
            binding.addToCart.setOnClickListener {
                onAddToCart(product)
            }
        }
    }
}