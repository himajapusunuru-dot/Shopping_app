package com.code4galaxy.ecommerceapp.response

import com.google.gson.annotations.SerializedName

data class PlaceOrderResponse(
    @SerializedName("status")
    val status: Int,

    @SerializedName("message")
    val message: String,

    @SerializedName("order_id")
    val orderId: Int?
)