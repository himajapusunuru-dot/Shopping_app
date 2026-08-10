package com.code4galaxy.ecommerceapp.request

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("full_name")
    val fullName: String,

    @SerializedName("mobile_no")
    val mobileNo: String,

    @SerializedName("email_id")
    val emailId: String,

    @SerializedName("password")
    val password: String
)