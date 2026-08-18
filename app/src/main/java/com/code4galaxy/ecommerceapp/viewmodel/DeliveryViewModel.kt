package com.code4galaxy.ecommerceapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.code4galaxy.ecommerceapp.UiState
import com.code4galaxy.ecommerceapp.repository.AddressRepository
import com.code4galaxy.ecommerceapp.request.AddAddressRequest
import com.code4galaxy.ecommerceapp.response.AddAddressResponse
import com.code4galaxy.ecommerceapp.response.AddressListResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DeliveryViewModel(
    private val repository: AddressRepository
): ViewModel(){
    private val _addAddressState = MutableLiveData<UiState<AddAddressResponse>>()
    val addAddressState: LiveData<UiState<AddAddressResponse>>
        get() = _addAddressState
    private val _addressListState = MutableLiveData<UiState<AddressListResponse>>()
    val addressListState: LiveData<UiState<AddressListResponse>>
        get() = _addressListState

    fun addAddress(request: AddAddressRequest){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _addAddressState.postValue((UiState.Loading))
                val response = repository.addAddress(request)
                if(response.isSuccessful){
                    response.body()?.let {
                        _addAddressState.postValue(UiState.Success(it))
                    }
                }
                else{
                    _addAddressState.postValue(UiState.Error(response.message()))
                }
            }catch (e: Exception){
                _addAddressState.postValue(UiState.Error(e.message ?: "Something went wrong"))
            }
        }
    }
    fun getAddressList(userId:String){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _addressListState.postValue(UiState.Loading)
                val response = repository.getAddress(userId)
                if(response.isSuccessful){
                    response.body()?.let {
                        _addressListState.postValue(UiState.Success(it))
                    }
                }
                else{
                    _addressListState.postValue(UiState.Error(response.message()))
                }
            }catch (e: Exception){
                _addressListState.postValue(UiState.Error(e.message ?: "Something went wrong"))
            }
        }
    }
    class DeliveryVMFactory(private val repository: AddressRepository): ViewModelProvider.Factory
    {
        override fun <T : ViewModel> create(modelClass: Class<T>): T
        {
            if(modelClass.isAssignableFrom(DeliveryViewModel::class.java)) {
                return DeliveryViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown Viewmodel class")
        }
    }

}