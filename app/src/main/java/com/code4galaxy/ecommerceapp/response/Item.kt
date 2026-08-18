package com.code4galaxy.ecommerceapp.response


import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("amount")
    val amount: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("product_id")
    val productId: String,
    @SerializedName("product_image_url")
    val productImageUrl: String,
    @SerializedName("product_name")
    val productName: String,
    @SerializedName("quantity")
    val quantity: String,
    @SerializedName("unit_price")
    val unitPrice: String
)