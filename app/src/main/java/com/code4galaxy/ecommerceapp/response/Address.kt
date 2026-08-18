package com.code4galaxy.ecommerceapp.response

import com.google.gson.annotations.SerializedName

data class Address(
    @SerializedName("address_id")
    val addressId: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("address")
    val address: String
)