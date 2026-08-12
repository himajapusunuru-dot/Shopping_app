package com.code4galaxy.ecommerceapp.utils

import androidx.recyclerview.widget.DiffUtil

class GenericDiffUtil<T: Any> (
    private val areItemsSame:(T,T) -> Boolean,
    private val areContainsSame:(T,T) -> Boolean = { oldItem,newItem ->
        oldItem == newItem
    }
):DiffUtil.ItemCallback<T> (){
    override fun areItemsTheSame(
        oldItem : T,
        newItem: T
    ): Boolean{
        return areItemsTheSame(oldItem,newItem)
    }

    override fun areContentsTheSame(
        oldItem : T,
        newItem: T
    ): Boolean{
        return areContentsTheSame(oldItem,newItem)
    }
}