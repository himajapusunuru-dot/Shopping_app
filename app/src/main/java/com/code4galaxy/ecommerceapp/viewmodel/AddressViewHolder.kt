package com.code4galaxy.ecommerceapp.viewmodel

import androidx.recyclerview.widget.RecyclerView
import com.code4galaxy.ecommerceapp.databinding.ItemAddressBinding
import com.code4galaxy.ecommerceapp.response.Address

class AddressViewHolder(
    private val  binding: ItemAddressBinding,
    private val onAddressSelected:(Address) -> Unit
) : RecyclerView.ViewHolder(binding.root){
    fun bind(address:Address){
        with(binding){
            addressTitle.text = address.title
            addressText.text = address.address
            root.setOnClickListener {
                onAddressSelected(address)
            }
            addressRadioButton.setOnClickListener {
                onAddressSelected(address)
            }
        }
    }
}