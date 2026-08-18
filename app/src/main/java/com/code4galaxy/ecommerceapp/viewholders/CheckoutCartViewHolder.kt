package com.code4galaxy.ecommerceapp.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.code4galaxy.ecommerceapp.R
import com.code4galaxy.ecommerceapp.databinding.ItemCheckoutCartBinding
import com.code4galaxy.ecommerceapp.model.local.CartItem

class CheckoutCartViewHolder(private val binding: ItemCheckoutCartBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(cartItem: CartItem){
        with(binding){
            productName.text = cartItem.productName
            unitPrice.text = "$ ${cartItem.price}"
            quantity.text = cartItem.quantity.toString()
            amount.text = "$ ${cartItem.price * cartItem.quantity}"
            val imageUrl = "http://gminnovex.com/myshop/images/" + cartItem.imageUrl.trimStart('/')
            Glide.with(productImage.context)
                .load(imageUrl)
                .error(R.drawable.error)
                .into(productImage)
        }
    }
}