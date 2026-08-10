package com.code4galaxy.ecommerceapp.request

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("email_id")
    val emailId: String,
    @SerializedName("password")
    val password: String
)