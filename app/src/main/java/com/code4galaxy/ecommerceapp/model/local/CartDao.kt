package com.code4galaxy.ecommerceapp.model.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlin.jvm.JvmSuppressWildcards

@Dao
@JvmSuppressWildcards
interface CartDao {

    @Insert
    suspend fun insertCart(cart: Cart): Long

    @Query(
        "SELECT * FROM cart WHERE userId = :userId AND isActive = 1 LIMIT 1"
    )
    suspend fun getActiveCart(
        userId: String
    ): Cart?

    @Insert
    suspend fun insertCartItem(
        cartItem: CartItem
    ): Long

    @Query(
        "SELECT * FROM cart_item WHERE cartId = :cartId"
    )
    fun getCartItems(
        cartId: Int
    ): LiveData<List<CartItem>>

    @Query(
        "SELECT * FROM cart_item WHERE cartId = :cartId AND productId = :productId LIMIT 1"
    )
    suspend fun getCartItem(
        cartId: Int,
        productId: String
    ): CartItem?

    @Query(
        "UPDATE cart_item SET quantity = :quantity WHERE cartItemId = :cartItemId"
    )
    suspend fun updateQuantity(
        cartItemId: Int,
        quantity: Int
    ): Int

    @Delete
    suspend fun deleteCartItem(
        cartItem: CartItem
    ): Int

    @Query("DELETE FROM cart_item WHERE cartId = :cartId")
    suspend fun clearCartItems(cartId: Int): Int
}
