package com.code4galaxy.ecommerceapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.code4galaxy.ecommerceapp.response.Address

class SharedCheckoutViewmodel: ViewModel() {
    private val _selectedAddress = MutableLiveData<Address>()
    val selectedAddress : LiveData<Address>
        get() = _selectedAddress
    private val _paymentMethod = MutableLiveData<String>()
    val paymentMethod: LiveData<String>
        get() = _paymentMethod

    fun setSelectedAddress(address: Address) {
        _selectedAddress.value = address
    }

    fun setPaymentMethod(paymentMethod: String) {
        _paymentMethod.value = paymentMethod
    }


}