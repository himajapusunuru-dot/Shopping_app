package com.code4galaxy.ecommerceapp.response


import com.google.gson.annotations.SerializedName

data class Subcategory(
    @SerializedName("category_id")
    val categoryId: String,
    @SerializedName("is_active")
    val isActive: String,
    @SerializedName("subcategory_id")
    val subcategoryId: String,
    @SerializedName("subcategory_image_url")
    val subcategoryImageUrl: String,
    @SerializedName("subcategory_name")
    val subcategoryName: String
)