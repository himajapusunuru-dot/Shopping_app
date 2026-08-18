package com.code4galaxy.ecommerceapp.repository

import com.code4galaxy.ecommerceapp.model.remote.ApiService
import com.code4galaxy.ecommerceapp.request.AddAddressRequest
import com.code4galaxy.ecommerceapp.response.AddAddressResponse
import com.code4galaxy.ecommerceapp.response.AddressListResponse
import retrofit2.Response

class AddressRepositoryImpl(private val apiService: ApiService): AddressRepository {
    override suspend fun addAddress(request: AddAddressRequest): Response<AddAddressResponse> {
        return apiService.addAddress(request)
    }

    override suspend fun getAddress(userId: String): Response<AddressListResponse> {
        return apiService.getAddresses(userId)
    }

}