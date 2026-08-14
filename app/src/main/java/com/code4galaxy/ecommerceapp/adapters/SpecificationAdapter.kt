package com.code4galaxy.ecommerceapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.code4galaxy.ecommerceapp.databinding.ItemSpecificationBinding
import com.code4galaxy.ecommerceapp.response.Specification
import com.code4galaxy.ecommerceapp.utils.GenericDiffUtil
import com.code4galaxy.ecommerceapp.viewholders.SpecificationViewHolder

class SpecificationAdapter: ListAdapter<Specification, SpecificationViewHolder>(
    GenericDiffUtil(
        areItemsSame = {oldItem,newItem ->
            oldItem.specificationId == newItem.specificationId
        }
    )
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SpecificationViewHolder {
        val binding =
            ItemSpecificationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        return SpecificationViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: SpecificationViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

}