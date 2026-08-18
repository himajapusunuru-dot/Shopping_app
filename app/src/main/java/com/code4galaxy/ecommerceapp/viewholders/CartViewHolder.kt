package com.code4galaxy.ecommerceapp.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.code4galaxy.ecommerceapp.databinding.ItemCartBinding
import com.code4galaxy.ecommerceapp.model.local.CartItem

class CartViewHolder(
    private val binding: ItemCartBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bind(cartItem: CartItem, onIncrease: (CartItem) -> Unit, onDecrease: (CartItem) -> Unit){
        with(binding){

            cartProductName.text =
                cartItem.productName

            cartDescription.text =
                cartItem.description

            unitPrice.text =
                "$ ${cartItem.price}"

            cartAmount.text =
                "$ ${cartItem.price * cartItem.quantity}"
            btnIncrease.setOnClickListener {
                onIncrease(cartItem)
            }
            btnDecrease.setOnClickListener {
                onDecrease(cartItem)
            }

            tvQuantity.text =
                cartItem.quantity.toString()
            val imageUrl = "http://gminnovex.com/myshop/images/" + cartItem.imageUrl.trimStart('/')
            Glide.with(cartProductImage.context)
                .load(imageUrl)
                .into(cartProductImage)
        }

    }
}