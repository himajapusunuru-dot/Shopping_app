package com.code4galaxy.ecommerceapp.viewholders

import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.code4galaxy.ecommerceapp.R
import com.code4galaxy.ecommerceapp.databinding.ItemCategoryBinding
import com.code4galaxy.ecommerceapp.response.Category

class CategoryViewHolder(
    private val binding: ItemCategoryBinding,
    private val onCategoryClick: (Category) -> Unit
): RecyclerView.ViewHolder(binding.root) {
    fun bind(category: Category) {

        binding.categoryName.text = category.categoryName

        val imageUrl = if (category.categoryImageUrl.startsWith("http")) {
            category.categoryImageUrl
        } else {
            "http://gminnovex.com/myshop/images/" + category.categoryImageUrl.trimStart('/')
        }

        Glide.with(binding.categoryImage.context)
            .load(imageUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_foreground)
            .into(binding.categoryImage)

        binding.root.setOnClickListener {
            onCategoryClick(category)
        }
    }
}