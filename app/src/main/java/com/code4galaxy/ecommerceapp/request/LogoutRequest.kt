package com.code4galaxy.ecommerceapp.request

import com.google.gson.annotations.SerializedName

data class LogoutRequest(
    @SerializedName("email_id")
    val emailId: String
)