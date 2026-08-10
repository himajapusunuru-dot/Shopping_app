package com.code4galaxy.ecommerceapp.response


import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("average_rating")
    val averageRating: String,
    @SerializedName("category_id")
    val categoryId: String,
    @SerializedName("category_name")
    val categoryName: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("product_id")
    val productId: String,
    @SerializedName("product_image_url")
    val productImageUrl: String,
    @SerializedName("product_name")
    val productName: String,
    @SerializedName("sub_category_id")
    val subCategoryId: String,
    @SerializedName("subcategory_name")
    val subcategoryName: String
)