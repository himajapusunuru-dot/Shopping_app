package com.code4galaxy.ecommerceapp.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.code4galaxy.ecommerceapp.databinding.ItemSpecificationBinding
import com.code4galaxy.ecommerceapp.response.Specification

class SpecificationViewHolder(private val binding: ItemSpecificationBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(specification: Specification){
        binding.specificationName.text = specification.title
        binding.specificationValue.text = specification.specification
    }
}