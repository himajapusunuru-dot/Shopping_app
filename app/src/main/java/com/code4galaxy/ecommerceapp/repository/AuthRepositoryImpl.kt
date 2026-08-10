package com.code4galaxy.ecommerceapp.repository

import com.code4galaxy.ecommerceapp.remote.ApiService
import com.code4galaxy.ecommerceapp.request.LoginRequest
import com.code4galaxy.ecommerceapp.request.RegisterRequest
import com.code4galaxy.ecommerceapp.response.LoginResponse
import com.code4galaxy.ecommerceapp.response.RegisterResponse

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

}