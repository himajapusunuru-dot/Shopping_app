package com.code4galaxy.ecommerceapp.repository

import com.code4galaxy.ecommerceapp.response.CategoryResponse
import com.code4galaxy.ecommerceapp.response.ProductDetailsResponse
import com.code4galaxy.ecommerceapp.response.ProductListResponse
import com.code4galaxy.ecommerceapp.response.SubCategoryResponse

interface ShopRepository {
    suspend fun getProductCategories() : CategoryResponse
    suspend fun getSubCategories(categoryId : String): SubCategoryResponse
    suspend fun getSubProducts(subCategoryId : String): ProductListResponse
    suspend fun getProductDetails(productId: String): ProductDetailsResponse
}