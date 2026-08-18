package com.code4galaxy.ecommerceapp.repository

import com.code4galaxy.ecommerceapp.request.PlaceOrderRequest
import com.code4galaxy.ecommerceapp.response.OrderDetailsResponse
import com.code4galaxy.ecommerceapp.response.PlaceOrderResponse
import retrofit2.Response

interface OrderRepository {

    suspend fun placeOrder(
        request: PlaceOrderRequest
    ): Response<PlaceOrderResponse>
    suspend fun getOrderDetails(
        orderId: String
    ): Response<OrderDetailsResponse>

}