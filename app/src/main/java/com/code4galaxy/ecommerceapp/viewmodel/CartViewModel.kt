package com.code4galaxy.ecommerceapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.code4galaxy.ecommerceapp.UiState
import com.code4galaxy.ecommerceapp.model.local.Cart
import com.code4galaxy.ecommerceapp.model.local.CartItem
import com.code4galaxy.ecommerceapp.repository.CartRepository
import com.code4galaxy.ecommerceapp.utils.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartViewModel(
    private val repository: CartRepository
) : ViewModel() {

    private val _cartState =
        MutableLiveData<UiState<Long>>()

    val cartState: LiveData<UiState<Long>>
        get() = _cartState

    private lateinit var sessionManager: SessionManager


    fun insertCart(cart: Cart) {

        viewModelScope.launch(Dispatchers.IO) {
            try {

                _cartState.postValue(UiState.Loading)

                val response =
                    repository.insertCart(cart)

                _cartState.postValue(
                    UiState.Success(response)
                )

            } catch (e: Exception) {

                _cartState.postValue(
                    UiState.Error(
                        e.message ?: "Something went wrong"
                    )
                )
            }
        }
    }


    fun insertCartItem(cartItem: CartItem) {

        viewModelScope.launch(Dispatchers.IO) {
            try {

                _cartState.postValue(UiState.Loading)

                val response =
                    repository.insertCartItem(cartItem)

                _cartState.postValue(
                    UiState.Success(response)
                )

            } catch (e: Exception) {

                _cartState.postValue(
                    UiState.Error(
                        e.message ?: "Something went wrong"
                    )
                )
            }
        }
    }


    fun addToCart(
        userId: String,
        productId: String,
        productName: String,
        description: String,
        price: Double,
        imageUrl: String
    ) {

        viewModelScope.launch(Dispatchers.IO) {

            try {

                _cartState.postValue(UiState.Loading)

                val activeCart =
                    repository.getActiveCart(userId)

                val cartId =
                    if (activeCart != null) {

                        activeCart.cartId

                    } else {

                        repository.insertCart(
                            Cart(userId = userId)
                        ).toInt()
                    }

                val existingItem =
                    repository.getCartItem(
                        cartId,
                        productId
                    )

                if (existingItem != null) {

                    repository.updateQuantity(
                        existingItem.cartItemId,
                        existingItem.quantity + 1
                    )

                    _cartState.postValue(
                        UiState.Success(
                            existingItem.cartItemId.toLong()
                        )
                    )

                } else {

                    val cartItem =
                        CartItem(
                            cartId = cartId,
                            productId = productId,
                            productName = productName,
                            description = description,
                            price = price,
                            imageUrl = imageUrl,
                            quantity = 1
                        )

                    val response =
                        repository.insertCartItem(cartItem)

                    _cartState.postValue(
                        UiState.Success(response)
                    )
                }

            } catch (e: Exception) {

                _cartState.postValue(
                    UiState.Error(
                        e.message ?: "Something Went Wrong"
                    )
                )
            }
        }
    }


    fun increaseQuantity(
        cartItem: CartItem
    ) {

        viewModelScope.launch(Dispatchers.IO) {

            try {

                repository.updateQuantity(
                    cartItem.cartItemId,
                    cartItem.quantity + 1
                )

            } catch (e: Exception) {

                _cartState.postValue(
                    UiState.Error(
                        e.message ?: "Something went wrong"
                    )
                )
            }
        }
    }
    fun decreaseQuantity(cartItem: CartItem) {

        viewModelScope.launch(Dispatchers.IO) {

            try {

                if (cartItem.quantity > 1) {

                    repository.updateQuantity(
                        cartItem.cartItemId,
                        cartItem.quantity - 1
                    )

                } else {

                    repository.deleteCartItem(cartItem)
                }

            } catch (e: Exception) {

                _cartState.postValue(
                    UiState.Error(
                        e.message ?: "Something went wrong"
                    )
                )
            }
        }
    }
    fun getCartItems(
        cartId: Int
    ): LiveData<List<CartItem>> {
        val userId =
        return repository.getCartItems(cartId)
    }
    fun getActiveCart(userId: String) : LiveData<Cart?>{
        return androidx.lifecycle.liveData(Dispatchers.IO){
            emit(repository.getActiveCart(userId))
        }
    }
    fun clearCartItems(cartId : Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.clearCartItems(cartId)
        }
    }


    class CartVMFactory(
        private val repository: CartRepository
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(
            modelClass: Class<T>
        ): T {

            if (
                modelClass.isAssignableFrom(
                    CartViewModel::class.java
                )
            ) {
                return CartViewModel(repository) as T
            }

            throw IllegalArgumentException(
                "Unknown ViewModel class"
            )
        }
    }
}
//    fun addToCart(
//        userId: String,
//        productId : String,
//        productName: String,
//        description : String,
//        price: Double,
//        imageUrl : String
//    ){
//        viewModelScope.launch(Dispatchers.IO) {
//            val activeCart = repository.getActiveCart(userId)
//            val cartId = if(activeCart != null){
//                activeCart.cartId
//
//            } else{
//                repository.insertCart(Cart(userId = userId)).toInt()
//            }
//            val cartItem = CartItem(
//                cartId = cartId,
//                productId = productId,
//                productName = productName,
//                description = description,
//                price = price,
//                imageUrl = imageUrl,
//                quantity = 1
//            )
//
//            repository.insertCartItem(cartItem)
//        }
//    }
//}

