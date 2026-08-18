package com.code4galaxy.ecommerceapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.code4galaxy.ecommerceapp.databinding.ItemCheckoutCartBinding
import com.code4galaxy.ecommerceapp.model.local.CartItem
import com.code4galaxy.ecommerceapp.utils.GenericDiffUtil
import com.code4galaxy.ecommerceapp.viewholders.CheckoutCartViewHolder

class CheckoutCartAdapter : ListAdapter<CartItem, CheckoutCartViewHolder>(
    GenericDiffUtil(areItemsSame = { oldItem, newItem ->
        oldItem.cartItemId == newItem.cartItemId
    })
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CheckoutCartViewHolder {
        val binding = ItemCheckoutCartBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CheckoutCartViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CheckoutCartViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }
}