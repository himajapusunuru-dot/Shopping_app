package com.code4galaxy.ecommerceapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.code4galaxy.ecommerceapp.UiState
import com.code4galaxy.ecommerceapp.repository.AuthRepository
import com.code4galaxy.ecommerceapp.request.LoginRequest
import com.code4galaxy.ecommerceapp.request.RegisterRequest
import com.code4galaxy.ecommerceapp.response.LoginResponse
import com.code4galaxy.ecommerceapp.response.RegisterResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: AuthRepository ): ViewModel() {
    private val _loginState = MutableLiveData<UiState<LoginResponse>>()
    val loginState: LiveData<UiState<LoginResponse>>
    get() = _loginState
    private val _registerState = MutableLiveData<UiState<RegisterResponse>>()
    val registerState: LiveData<UiState<RegisterResponse>>
        get() = _registerState
    fun login(email : String,password:String){
        _loginState.value = UiState.Loading
        viewModelScope.launch (Dispatchers.IO){
            try{
                val loginRequest = LoginRequest(emailId = email,
                    password = password)
                val response = repository.loginUser(loginRequest)
                if(response.status == 0){
                    _loginState.postValue(UiState.Success(response))
                }
                else{
                    _loginState.postValue(UiState.Error(response.message))
                }
            }
            catch (e: Exception){
                _loginState.postValue(UiState.Error(e.message ?: "Login Failed"))
            }
        }

    }
    fun register(fullName: String, mobileNo: String, email: String,password: String){
        viewModelScope.launch(Dispatchers.IO) {
            try{
                val registerRequest= RegisterRequest(fullName =fullName,mobileNo = mobileNo, emailId = email,password=password )
                val response = repository.registerUser(registerRequest)
                if(response.status == 0){
                    _registerState.postValue(UiState.Success(response))
                }
                else{
                    _registerState.postValue(UiState.Error(response.message))
                }
            }
            catch (e: Exception){
                _registerState.postValue(UiState.Error(e.message?:"Registration Failed"))
            }
        }
    }




}