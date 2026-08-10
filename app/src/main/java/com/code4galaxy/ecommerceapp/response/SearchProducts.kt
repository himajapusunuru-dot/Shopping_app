package com.code4galaxy.ecommerceapp.response


import com.google.gson.annotations.SerializedName

data class SearchProducts(
    @SerializedName("message")
    val message: String,
    @SerializedName("product")
    val product: Product,
    @SerializedName("status")
    val status: Int
)