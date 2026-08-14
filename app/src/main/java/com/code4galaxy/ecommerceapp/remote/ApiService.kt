package com.code4galaxy.ecommerceapp.remote

import com.code4galaxy.ecommerceapp.request.LoginRequest
import com.code4galaxy.ecommerceapp.request.RegisterRequest
import com.code4galaxy.ecommerceapp.response.CategoryResponse
import com.code4galaxy.ecommerceapp.response.LoginResponse
import com.code4galaxy.ecommerceapp.response.ProductDetailsResponse
import com.code4galaxy.ecommerceapp.response.ProductListResponse
import com.code4galaxy.ecommerceapp.response.RegisterResponse
import com.code4galaxy.ecommerceapp.response.SubCategoryResponse
import com.code4galaxy.ecommerceapp.view.RegisterFragment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @Headers("Content-type: application/json")
    @POST("User/auth")
    suspend fun loginUser(
        @Body loginRequest: LoginRequest
    ): Response<LoginResponse>

    @Headers("Content-type: application/json")
    @POST("User/register")
    suspend fun registerUser(
        @Body registerRequest: RegisterRequest
    ) : Response<RegisterResponse>
    @GET("Category")
    suspend fun  getProductCategories(): Response<CategoryResponse>

    @GET("SubCategory")
    suspend fun getSubcategories(
        @Query("category_id") categoryId: String
    ): Response<SubCategoryResponse>

    @GET("SubCategory/products/{sub_category_id}")
    suspend fun getProducts(
        @Path("sub_category_id") subCatergoryId : String
    ): Response<ProductListResponse>
    @GET("Product/details/{product_id}")
    suspend fun getProductDetails(
        @Path("product_id") productId : String
    ): Response<ProductDetailsResponse>
}