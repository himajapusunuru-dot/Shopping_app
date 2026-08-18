package com.code4galaxy.ecommerceapp.repository

import androidx.lifecycle.LiveData
import com.code4galaxy.ecommerceapp.model.local.Cart
import com.code4galaxy.ecommerceapp.model.local.CartDao
import com.code4galaxy.ecommerceapp.model.local.CartItem

class CartRepositoryImpl(
    private val cartDao: CartDao
): CartRepository {
    override suspend fun insertCart(cart: Cart): Long {
        return cartDao.insertCart(cart)
    }

    override suspend fun getActiveCart(userId: String): Cart? {
        return cartDao.getActiveCart(userId)
    }

    override suspend fun insertCartItem(cartItem: CartItem): Long {
        return cartDao.insertCartItem(cartItem)
    }

    override fun getCartItems(cartId: Int): LiveData<List<CartItem>> {
        return cartDao.getCartItems(cartId)
    }

    override suspend fun getCartItem(
        cartId: Int,
        productId: String
    ): CartItem? {
        return cartDao.getCartItem(cartId,productId)
    }
    override suspend fun updateQuantity(
        cartItemId: Int,
        quantity: Int
    ): Int {
        return cartDao.updateQuantity(
            cartItemId,
            quantity
        )
    }

    override suspend fun deleteCartItem(cartItem: CartItem): Int {
        return cartDao.deleteCartItem(cartItem)
    }

    override suspend fun clearCartItems(cartId: Int): Int {
        return cartDao.clearCartItems(cartId)
    }
}