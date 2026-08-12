package com.code4galaxy.ecommerceapp.repository

import com.code4galaxy.ecommerceapp.remote.ApiService
import com.code4galaxy.ecommerceapp.response.CategoryResponse

class ShopRepositoryImpl(private val apiService: ApiService): ShopRepository {
    override suspend fun getProductCategories(): CategoryResponse {
        val response = apiService.getProductCategories()
        if(!response.isSuccessful){
            throw RuntimeException("Error in Loading Categories")
        }
        return response.body() ?: throw RuntimeException("Empty Response From Response")
    }

}