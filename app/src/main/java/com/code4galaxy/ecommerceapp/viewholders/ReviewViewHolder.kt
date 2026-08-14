package com.code4galaxy.ecommerceapp.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.code4galaxy.ecommerceapp.databinding.ItemReviewBinding
import com.code4galaxy.ecommerceapp.response.Review

class ReviewViewHolder(private val binding: ItemReviewBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(review: Review) {

        binding.reviewUserName.text =
            review.fullName

        binding.reviewTitle.text =
            review.reviewTitle

        binding.reviewDescription.text =
            review.review

        binding.reviewRating.rating =
            review.rating.toFloatOrNull() ?: 0f
    }
}