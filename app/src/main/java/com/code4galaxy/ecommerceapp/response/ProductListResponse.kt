package com.code4galaxy.ecommerceapp.response


import com.google.gson.annotations.SerializedName

data class ProductListResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("products")
    val products: List<Product>,
    @SerializedName("status")
    val status: Int
)