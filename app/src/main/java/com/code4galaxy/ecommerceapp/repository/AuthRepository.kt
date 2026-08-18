package com.code4galaxy.ecommerceapp.repository

import com.code4galaxy.ecommerceapp.request.LoginRequest
import com.code4galaxy.ecommerceapp.request.LogoutRequest
import com.code4galaxy.ecommerceapp.request.RegisterRequest
import com.code4galaxy.ecommerceapp.response.CategoryResponse
import com.code4galaxy.ecommerceapp.response.LoginResponse
import com.code4galaxy.ecommerceapp.response.LogoutResponse
import com.code4galaxy.ecommerceapp.response.RegisterResponse
import retrofit2.Response

interface AuthRepository {
    suspend fun loginUser(
        loginRequest: LoginRequest
    ): LoginResponse
    suspend fun registerUser(
        registerRequest: RegisterRequest
    ): RegisterResponse
    suspend fun logout(
        request: LogoutRequest
    ): Response<LogoutResponse>

}