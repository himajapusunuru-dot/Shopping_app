package com.code4galaxy.ecommerceapp.response


import com.google.gson.annotations.SerializedName

data class SubCategoryResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    var status: Int,
    @SerializedName("subcategories")
    val subcategories: List<Subcategory>
)