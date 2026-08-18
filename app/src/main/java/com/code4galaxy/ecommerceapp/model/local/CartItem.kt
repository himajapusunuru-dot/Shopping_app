package com.code4galaxy.ecommerceapp.model.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity("cart_Item", foreignKeys = [ForeignKey(entity = Cart::class,
        parentColumns = ["cartId"],
        childColumns = ["cartId"],
        onDelete = ForeignKey.CASCADE)],
    indices = [Index("cartId")])
data class CartItem (
    @PrimaryKey(autoGenerate = true)
    val cartItemId : Int =0,
    val cartId : Int,
    val productId :  String,
    val productName : String,
    val description : String,
    val price : Double,
    val imageUrl : String,
    val quantity : Int =1

)