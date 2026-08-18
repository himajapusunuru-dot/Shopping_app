package com.code4galaxy.ecommerceapp.repository

import androidx.lifecycle.LiveData
import com.code4galaxy.ecommerceapp.model.local.Cart
import com.code4galaxy.ecommerceapp.model.local.CartItem

interface CartRepository {
    suspend fun insertCart(cart: Cart): Long
    suspend fun getActiveCart(userId: String): Cart?
    suspend fun insertCartItem(cartItem: CartItem): Long
    fun getCartItems(cartId: Int): LiveData<List<CartItem>>
    suspend fun getCartItem(cartId: Int, productId: String): CartItem?
    suspend fun updateQuantity(cartItemId: Int,quantity:Int): Int
    suspend fun deleteCartItem(cartItem: CartItem): Int
    suspend fun clearCartItems(cartId: Int): Int
}