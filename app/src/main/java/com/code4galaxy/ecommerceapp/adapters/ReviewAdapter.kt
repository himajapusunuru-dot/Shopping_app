package com.code4galaxy.ecommerceapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.code4galaxy.ecommerceapp.databinding.ItemReviewBinding
import com.code4galaxy.ecommerceapp.response.Review
import com.code4galaxy.ecommerceapp.utils.GenericDiffUtil
import com.code4galaxy.ecommerceapp.viewholders.ReviewViewHolder

class ReviewAdapter: ListAdapter<Review, ReviewViewHolder>(
    GenericDiffUtil(areItemsSame = {oldItem,newItem ->
        oldItem.reviewId == newItem.reviewId
    })
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReviewViewHolder {
       val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ReviewViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ReviewViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }
}