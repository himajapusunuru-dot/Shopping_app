package com.code4galaxy.ecommerceapp.repository

import com.code4galaxy.ecommerceapp.remote.ApiService
import com.code4galaxy.ecommerceapp.response.CategoryResponse
import com.code4galaxy.ecommerceapp.response.ProductListResponse
import com.code4galaxy.ecommerceapp.response.SubCategoryResponse

class ShopRepositoryImpl(private val apiService: ApiService): ShopRepository {
    override suspend fun getProductCategories(): CategoryResponse {
        val response = apiService.getProductCategories()
        if(!response.isSuccessful){
            throw RuntimeException("Error in Loading Categories")
        }
        return response.body() ?: throw RuntimeException("Empty Response From Response")
    }

    override suspend fun getSubCategories(categoryId: String): SubCategoryResponse {
        val response = apiService.getSubcategories(categoryId)
        if (!response.isSuccessful) {
            throw RuntimeException("Unable to load subcategories")
        }

        return response.body()
            ?: throw RuntimeException("Empty response")
    }

    override suspend fun getSubProducts(subCategoryId: String): ProductListResponse {
        val response =
            apiService.getProducts(subCategoryId)

        if (!response.isSuccessful) {
            throw RuntimeException("Unable to load products")
        }

        return response.body()
            ?: throw RuntimeException("Empty response")
    }

}