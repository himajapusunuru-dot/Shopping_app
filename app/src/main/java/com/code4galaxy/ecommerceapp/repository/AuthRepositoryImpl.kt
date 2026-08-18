package com.code4galaxy.ecommerceapp.repository

import com.code4galaxy.ecommerceapp.model.remote.ApiService
import com.code4galaxy.ecommerceapp.request.LoginRequest
import com.code4galaxy.ecommerceapp.request.LogoutRequest
import com.code4galaxy.ecommerceapp.request.RegisterRequest
import com.code4galaxy.ecommerceapp.response.LoginResponse
import com.code4galaxy.ecommerceapp.response.LogoutResponse
import com.code4galaxy.ecommerceapp.response.RegisterResponse
import retrofit2.Response

class AuthRepositoryImpl(private val apiService: ApiService) : AuthRepository {
    override suspend fun loginUser(loginRequest: LoginRequest): LoginResponse {
        val response = apiService.loginUser(loginRequest)
        if(!response.isSuccessful){
            throw RuntimeException("Error While Logging in")
        }
        return response.body()?: throw RuntimeException(
            "Empty response from Server"
        )
    }

    override suspend fun registerUser(registerRequest: RegisterRequest): RegisterResponse {
        val response = apiService.registerUser(registerRequest)
        if(!response.isSuccessful){
            throw RuntimeException("Error While Registering")
        }
        return response.body() ?: throw RuntimeException("Empty response from server")
    }
    override suspend fun logout(
        request: LogoutRequest
    ): Response<LogoutResponse> {
        return apiService.logout(request)
    }



}