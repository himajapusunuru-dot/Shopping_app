package com.code4galaxy.ecommerceapp.viewholders

import android.graphics.drawable.Drawable
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
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
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    Log.e("CategoryViewHolder", "Image load failed for URL: $imageUrl", e)
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>?,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }
            })
            .into(binding.categoryImage)

        binding.root.setOnClickListener {
            onCategoryClick(category)
        }
    }
}
