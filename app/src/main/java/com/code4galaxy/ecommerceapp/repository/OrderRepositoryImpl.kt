package com.code4galaxy.ecommerceapp.repository

import com.code4galaxy.ecommerceapp.model.remote.ApiService
import com.code4galaxy.ecommerceapp.request.PlaceOrderRequest
import com.code4galaxy.ecommerceapp.response.OrderDetailsResponse
import com.code4galaxy.ecommerceapp.response.PlaceOrderResponse
import retrofit2.Response

class OrderRepositoryImpl (
    private val apiService: ApiService
): OrderRepository{
    override suspend fun placeOrder(request: PlaceOrderRequest): Response<PlaceOrderResponse> {
        return apiService.placeOrder(request)
    }

    override suspend fun getOrderDetails(orderId: String): Response<OrderDetailsResponse> {
        return apiService.getOrderDetails(orderId)
    }
}