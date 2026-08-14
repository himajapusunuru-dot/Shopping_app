package com.code4galaxy.ecommerceapp.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.code4galaxy.ecommerceapp.R
import com.code4galaxy.ecommerceapp.databinding.ItemProductBinding
import com.code4galaxy.ecommerceapp.response.Product

class ProductViewHolder (
    private val binding: ItemProductBinding,
    private val onProductClick:(Product)-> Unit
): RecyclerView.ViewHolder(binding.root){
    fun bind(product: Product){
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
    }
}