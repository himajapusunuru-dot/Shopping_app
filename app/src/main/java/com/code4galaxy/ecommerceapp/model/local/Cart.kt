package com.code4galaxy.ecommerceapp.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("cart")
data class Cart(
    @PrimaryKey(autoGenerate = true)
    val cartId : Int = 0,
    val userId: String,
    val isActive : Boolean = true
)