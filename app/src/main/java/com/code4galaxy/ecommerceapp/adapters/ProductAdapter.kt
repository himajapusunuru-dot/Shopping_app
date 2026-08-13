package com.code4galaxy.ecommerceapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.code4galaxy.ecommerceapp.databinding.ItemProductBinding
import com.code4galaxy.ecommerceapp.response.Product
import com.code4galaxy.ecommerceapp.utils.GenericDiffUtil
import com.code4galaxy.ecommerceapp.viewholders.ProductViewHolder

class ProductAdapter(
    private val onProductClick : (Product) -> Unit
): ListAdapter<Product, ProductViewHolder>(GenericDiffUtil<Product>(
    areItemsSame = {oldItem,newItem->
        oldItem.productId == newItem.productId
    }
)) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ProductViewHolder(binding, onProductClick)
    }

    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }
}