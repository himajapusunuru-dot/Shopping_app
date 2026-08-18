package com.code4galaxy.ecommerceapp.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.code4galaxy.ecommerceapp.databinding.ItemOrderConfirmedBinding
import com.code4galaxy.ecommerceapp.response.Item

class OrderConfirmedViewHolder(private val binding: ItemOrderConfirmedBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Item){
        with(binding){
            tvProductName.text = item.productName
            tvUnitPrice.text = "$${item.unitPrice}"
            tvQuantity.text = "Qty: ${item.quantity}"
            tvAmount.text = "$${item.amount}"

            Glide.with(ivProductImage.context)
                .load("http://gminnovex.com/myshop/images/"+item.productImageUrl)
                .into(ivProductImage)

        }
    }
}