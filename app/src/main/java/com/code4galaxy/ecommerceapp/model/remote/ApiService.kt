package com.code4galaxy.ecommerceapp.model.remote

import com.code4galaxy.ecommerceapp.request.AddAddressRequest
import com.code4galaxy.ecommerceapp.request.LoginRequest
import com.code4galaxy.ecommerceapp.request.LogoutRequest
import com.code4galaxy.ecommerceapp.request.PlaceOrderRequest
import com.code4galaxy.ecommerceapp.request.RegisterRequest
import com.code4galaxy.ecommerceapp.response.AddAddressResponse
import com.code4galaxy.ecommerceapp.response.AddressListResponse
import com.code4galaxy.ecommerceapp.response.CategoryResponse
import com.code4galaxy.ecommerceapp.response.LoginResponse
import com.code4galaxy.ecommerceapp.response.LogoutResponse
import com.code4galaxy.ecommerceapp.response.OrderDetailsResponse
import com.code4galaxy.ecommerceapp.response.PlaceOrderResponse
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

    @Headers("Content-Type: application/json")
    @POST("User/address")
    suspend fun addAddress(
        @Body request: AddAddressRequest
    ): Response<AddAddressResponse>
    @GET("User/addresses/{user_id}")
    suspend fun getAddresses(
        @Path("user_id") userId: String
    ): Response<AddressListResponse>

    @Headers("Content-Type: application/json")
    @POST("Order")
    suspend fun placeOrder(
        @Body request: PlaceOrderRequest
    ): Response<PlaceOrderResponse>
    @Headers("Content-Type: application/json")
    @POST("User/logout")
    suspend fun logout(
        @Body request: LogoutRequest
    ): Response<LogoutResponse>
    @GET("Order")
    suspend fun getOrderDetails(
        @Query("order_id") orderId: String
    ): Response<OrderDetailsResponse>

}