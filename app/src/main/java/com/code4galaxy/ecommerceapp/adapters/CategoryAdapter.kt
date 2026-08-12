package com.code4galaxy.ecommerceapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.code4galaxy.ecommerceapp.databinding.ItemCategoryBinding
import com.code4galaxy.ecommerceapp.response.Category
import com.code4galaxy.ecommerceapp.utils.GenericDiffUtil
import com.code4galaxy.ecommerceapp.viewholders.CategoryViewHolder

class CategoryAdapter(
    private val onCategoryClick : (Category) -> Unit,
): ListAdapter<Category, CategoryViewHolder>(
    GenericDiffUtil<Category>(
        areItemsSame = {oldItem,newItem ->
            oldItem.categoryId == newItem.categoryId
        }
    )
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CategoryViewHolder(binding,onCategoryClick)
    }

    override fun onBindViewHolder(
        holder: CategoryViewHolder,
        position: Int
    ) {
        holder.bind(
        getItem(position))
    }
}