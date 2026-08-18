package com.code4galaxy.ecommerceapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.code4galaxy.ecommerceapp.UiState
import com.code4galaxy.ecommerceapp.repository.OrderRepository
import com.code4galaxy.ecommerceapp.repository.OrderRepositoryImpl
import com.code4galaxy.ecommerceapp.request.PlaceOrderRequest
import com.code4galaxy.ecommerceapp.response.OrderDetailsResponse
import com.code4galaxy.ecommerceapp.response.PlaceOrderResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OrderViewModel(
    private val repository: OrderRepository
): ViewModel() {
    private val _placeOrderState = MutableLiveData<UiState<PlaceOrderResponse>>()

    val placeOrderState: LiveData<UiState<PlaceOrderResponse>>
        get() = _placeOrderState

    private val _orderDetailsState =
        MutableLiveData<UiState<OrderDetailsResponse>>()

    val orderDetailsState: LiveData<UiState<OrderDetailsResponse>>
        get() = _orderDetailsState
    fun placeOrder(request: PlaceOrderRequest){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _placeOrderState.postValue(UiState.Loading)

                val response =
                    repository.placeOrder(request)

                if (response.isSuccessful) {

                    response.body()?.let {
                        _placeOrderState.postValue(
                            UiState.Success(it)
                        )
                    }

                } else {

                    _placeOrderState.postValue(
                        UiState.Error(response.message())
                    )
                }

            }catch (e: Exception){
                _placeOrderState.postValue(UiState.Error(e.message?:"Something went wrong"))
            }
        }
    }
    class OrderVMFactory(
        private val repository: OrderRepository
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(
            modelClass: Class<T>
        ): T {

            if (
                modelClass.isAssignableFrom(
                    OrderViewModel::class.java
                )
            ) {
                return OrderViewModel(repository) as T
            }

            throw IllegalArgumentException(
                "Unknown ViewModel class"
            )
        }
    }
    fun getOrderDetails(orderId: String) {

        viewModelScope.launch(Dispatchers.IO) {

            try {

                _orderDetailsState.postValue(UiState.Loading)

                val response =
                    repository.getOrderDetails(orderId)

                if (response.isSuccessful) {

                    response.body()?.let {

                        _orderDetailsState.postValue(
                            UiState.Success(it)
                        )
                    }

                } else {

                    _orderDetailsState.postValue(
                        UiState.Error(response.message())
                    )
                }

            } catch (e: Exception) {

                _orderDetailsState.postValue(
                    UiState.Error(
                        e.message ?: "Something went wrong"
                    )
                )
            }
        }
    }
}