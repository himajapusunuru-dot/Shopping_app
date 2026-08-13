package com.code4galaxy.ecommerceapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.code4galaxy.ecommerceapp.UiState
import com.code4galaxy.ecommerceapp.repository.ShopRepository
import com.code4galaxy.ecommerceapp.response.CategoryResponse
import com.code4galaxy.ecommerceapp.utils.ApiStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val repository: ShopRepository
) : ViewModel() {
    private val _categoryState = MutableLiveData<UiState<CategoryResponse>>()
     val categoryState : LiveData<UiState<CategoryResponse>>
        get() = _categoryState
    fun getCategories(){
        _categoryState.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getProductCategories()
                if(response.status == ApiStatus.SUCCESS){
                    _categoryState.postValue((UiState.Success(response)))
                }
                else{
                    _categoryState.postValue(UiState.Error(response.message))
                }

            }catch (e : Exception){
                _categoryState.postValue((UiState.Error(e.message?:"Failed to load Categories")))
            }
        }

    }

}

class CategoryVMFactory(private val repo: ShopRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CategoryViewModel(repo) as T
    }
}