package com.code4galaxy.ecommerceapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.code4galaxy.ecommerceapp.UiState
import com.code4galaxy.ecommerceapp.repository.AuthRepository
import com.code4galaxy.ecommerceapp.request.LoginRequest
import com.code4galaxy.ecommerceapp.request.LogoutRequest
import com.code4galaxy.ecommerceapp.request.RegisterRequest
import com.code4galaxy.ecommerceapp.response.LoginResponse
import com.code4galaxy.ecommerceapp.response.LogoutResponse
import com.code4galaxy.ecommerceapp.response.RegisterResponse
import com.code4galaxy.ecommerceapp.utils.ApiStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: AuthRepository ): ViewModel() {
    private val _loginState = MutableLiveData<UiState<LoginResponse>>()
    val loginState: LiveData<UiState<LoginResponse>>
    get() = _loginState
    private val _registerState = MutableLiveData<UiState<RegisterResponse>>()
    val registerState: LiveData<UiState<RegisterResponse>>
        get() = _registerState
    private val _logoutState =
        MutableLiveData<UiState<LogoutResponse>>()

    val logoutState: LiveData<UiState<LogoutResponse>>
        get() = _logoutState
    fun login(email : String,password:String){
        _loginState.value = UiState.Loading
        viewModelScope.launch (Dispatchers.IO){
            try{
                val loginRequest = LoginRequest(emailId = email,
                    password = password)
                val response = repository.loginUser(loginRequest)
                if(response.status == ApiStatus.SUCCESS){
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
        _registerState.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try{
                val registerRequest= RegisterRequest(fullName =fullName,mobileNo = mobileNo, emailId = email,password=password )
                val response = repository.registerUser(registerRequest)
                if(response.status == ApiStatus.SUCCESS){
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
    fun logout(request: LogoutRequest) {

        viewModelScope.launch(Dispatchers.IO) {

            try {

                _logoutState.postValue(UiState.Loading)

                val response = repository.logout(request)

                if (response.isSuccessful) {

                    response.body()?.let {
                        _logoutState.postValue(
                            UiState.Success(it)
                        )
                    }

                } else {

                    _logoutState.postValue(
                        UiState.Error(response.message())
                    )
                }

            } catch (e: Exception) {

                _logoutState.postValue(
                    UiState.Error(
                        e.message ?: "Something went wrong"
                    )
                )
            }
        }
    }

}

class AuthVMFactory(val repo : AuthRepository): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AuthViewModel(repo) as T
    }
}

