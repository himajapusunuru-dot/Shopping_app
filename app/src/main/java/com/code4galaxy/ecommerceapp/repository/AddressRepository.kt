package com.code4galaxy.ecommerceapp.repository

import com.code4galaxy.ecommerceapp.request.AddAddressRequest
import com.code4galaxy.ecommerceapp.response.AddAddressResponse
import com.code4galaxy.ecommerceapp.response.AddressListResponse
import retrofit2.Response

interface AddressRepository {
    suspend fun addAddress(request: AddAddressRequest) : Response<AddAddressResponse>
    suspend fun getAddress(userId: String): Response<AddressListResponse>
}