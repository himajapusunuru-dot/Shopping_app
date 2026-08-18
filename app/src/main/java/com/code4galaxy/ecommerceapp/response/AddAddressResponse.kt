package com.code4galaxy.ecommerceapp.response

import com.google.gson.annotations.SerializedName

data class AddAddressResponse(
    @SerializedName("status")
    val status: Int,

    @SerializedName("message")
    val message: String
)