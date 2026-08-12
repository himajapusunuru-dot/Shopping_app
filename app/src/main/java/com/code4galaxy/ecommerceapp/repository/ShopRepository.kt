package com.code4galaxy.ecommerceapp.repository

import com.code4galaxy.ecommerceapp.response.CategoryResponse

interface ShopRepository {
    suspend fun getProductCategories() : CategoryResponse
}