package com.code4galaxy.ecommerceapp.response


import com.google.gson.annotations.SerializedName

data class Order(
    @SerializedName("address")
    val address: String,
    @SerializedName("address_title")
    val addressTitle: String,
    @SerializedName("bill_amount")
    val billAmount: String,
    @SerializedName("items")
    val items: List<Item>,
    @SerializedName("order_date")
    val orderDate: String,
    @SerializedName("order_id")
    val orderId: String,
    @SerializedName("order_status")
    val orderStatus: String,
    @SerializedName("payment_method")
    val paymentMethod: String
)