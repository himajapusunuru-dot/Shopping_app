package com.code4galaxy.ecommerceapp.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.code4galaxy.ecommerceapp.databinding.ItemProductImageBinding
import retrofit2.http.Path

class ImageViewHolder(private val binding: ItemProductImageBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(imagePath: String){
        val imageUrl = "http://gminnovex.com/myshop/images/" + imagePath.trimStart('/')
        Glide.with(binding.productImage.context)
            .load(imageUrl)
            .into(binding.productImage)
    }
}