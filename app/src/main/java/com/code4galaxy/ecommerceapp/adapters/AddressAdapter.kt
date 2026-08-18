package com.code4galaxy.ecommerceapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.code4galaxy.ecommerceapp.databinding.ItemAddressBinding
import com.code4galaxy.ecommerceapp.response.Address
import com.code4galaxy.ecommerceapp.utils.GenericDiffUtil
import com.code4galaxy.ecommerceapp.viewmodel.AddressViewHolder

class AddressAdapter (
    private val onAddressSelected:(Address) -> Unit
): ListAdapter<Address, AddressViewHolder>(
    GenericDiffUtil(areItemsSame = {oldItem,newItem->
        oldItem.addressId == newItem.addressId
    })
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AddressViewHolder {
        val binding = ItemAddressBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AddressViewHolder(binding,onAddressSelected)
    }

    override fun onBindViewHolder(
        holder: AddressViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }
}