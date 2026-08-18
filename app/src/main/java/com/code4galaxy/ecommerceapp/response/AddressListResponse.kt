package com.code4galaxy.ecommerceapp.response

import com.google.gson.annotations.SerializedName

data class AddressListResponse(
    @SerializedName("status")
    val status: Int,

    @SerializedName("message")
    val message: String,

    @SerializedName("addresses")
    val addresses: List<Address>
)