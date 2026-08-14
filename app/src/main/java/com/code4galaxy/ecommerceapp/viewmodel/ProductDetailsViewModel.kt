package com.code4galaxy.ecommerceapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.code4galaxy.ecommerceapp.UiState
import com.code4galaxy.ecommerceapp.repository.ShopRepository
import com.code4galaxy.ecommerceapp.response.ProductDetailsResponse
import com.code4galaxy.ecommerceapp.utils.ApiStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductDetailsViewModel(
    private val repository: ShopRepository
): ViewModel() {
    private val _productDetailsState = MutableLiveData<UiState<ProductDetailsResponse>>()
    val productDetailsState: LiveData<UiState<ProductDetailsResponse>>
    get() = _productDetailsState
    fun getProductDetails(productId : String){
        _productDetailsState.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getProductDetails(productId)
                if(response.status == ApiStatus.SUCCESS){
                    _productDetailsState.postValue(UiState.Success(response))
                }
                else{
                    _productDetailsState.postValue(UiState.Error(response.message))
                }

            }catch (e:Exception){
                _productDetailsState.postValue(UiState.Error(e.message ?: "Something went wrong"))
            }
        }
    }
    class ProductDetailsVMFactory(
        private val repository: ShopRepository
    ): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ProductDetailsViewModel(repository) as T
        }
    }
}