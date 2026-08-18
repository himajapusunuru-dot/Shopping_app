package com.code4galaxy.ecommerceapp.response

import com.google.gson.annotations.SerializedName

data class OrderAddress(
    @SerializedName("title")
    val title: String,

    @SerializedName("address")
    val address: String
)