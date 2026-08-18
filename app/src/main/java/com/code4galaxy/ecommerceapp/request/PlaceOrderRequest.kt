package com.code4galaxy.ecommerceapp.request


import com.code4galaxy.ecommerceapp.response.OrderAddress
import com.code4galaxy.ecommerceapp.response.OrderItem
import com.google.gson.annotations.SerializedName

data class PlaceOrderRequest(

    @SerializedName("user_id")
    val userId: Int,

    @SerializedName("delivery_address")
    val deliveryAddress: OrderAddress,

    @SerializedName("items")
    val items: List<OrderItem>,

    @SerializedName("bill_amount")
    val billAmount: Double,

    @SerializedName("payment_method")
    val paymentMethod: String
)