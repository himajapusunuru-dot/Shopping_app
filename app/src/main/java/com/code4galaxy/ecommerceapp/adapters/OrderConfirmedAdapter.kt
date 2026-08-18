package com.code4galaxy.ecommerceapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.code4galaxy.ecommerceapp.databinding.ItemOrderConfirmedBinding
import com.code4galaxy.ecommerceapp.response.Item
import com.code4galaxy.ecommerceapp.utils.GenericDiffUtil
import com.code4galaxy.ecommerceapp.viewholders.OrderConfirmedViewHolder

class OrderConfirmedAdapter :
    ListAdapter<Item, OrderConfirmedViewHolder>(
        GenericDiffUtil(
            areItemsSame = { old, new ->
                old.productId == new.productId
            }
        )
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrderConfirmedViewHolder {

        val binding =
            ItemOrderConfirmedBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        return OrderConfirmedViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: OrderConfirmedViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }
}
