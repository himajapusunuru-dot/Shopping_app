package com.code4galaxy.ecommerceapp.request


import com.google.gson.annotations.SerializedName

data class DeliveryAddress(
    @SerializedName("address")
    val address: String,
    @SerializedName("title")
    val title: String
)