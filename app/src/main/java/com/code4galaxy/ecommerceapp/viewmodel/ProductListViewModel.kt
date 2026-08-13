package com.code4galaxy.ecommerceapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.code4galaxy.ecommerceapp.UiState
import com.code4galaxy.ecommerceapp.repository.ShopRepository
import com.code4galaxy.ecommerceapp.response.ProductListResponse
import com.code4galaxy.ecommerceapp.response.SubCategoryResponse
import com.code4galaxy.ecommerceapp.utils.ApiStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductListViewModel(
    private val repository: ShopRepository
) : ViewModel() {

    private val _subCategory =
        MutableLiveData<UiState<SubCategoryResponse>>()

    val subCategory: LiveData<UiState<SubCategoryResponse>>
        get() = _subCategory


    private val _productState =
        MutableLiveData<UiState<ProductListResponse>>()

    val productState: LiveData<UiState<ProductListResponse>>
        get() = _productState


    fun getSubCategories(categoryId: String) {

        _subCategory.value = UiState.Loading

        viewModelScope.launch(Dispatchers.IO) {

            try {

                val response =
                    repository.getSubCategories(categoryId)

                if (response.status == ApiStatus.SUCCESS) {

                    _subCategory.postValue(
                        UiState.Success(response)
                    )

                } else {

                    _subCategory.postValue(
                        UiState.Error(response.message)
                    )
                }

            } catch (e: Exception) {

                _subCategory.postValue(
                    UiState.Error(
                        e.message ?: "Something went wrong"
                    )
                )
            }
        }
    }


    fun getSubProducts(subcategoryId: String) {

        _productState.value = UiState.Loading

        viewModelScope.launch(Dispatchers.IO) {

            try {

                val response =
                    repository.getSubProducts(subcategoryId)

                if (response.status == ApiStatus.SUCCESS) {

                    _productState.postValue(
                        UiState.Success(response)
                    )

                } else {

                    _productState.postValue(
                        UiState.Error(response.message)
                    )
                }

            } catch (e: Exception) {

                _productState.postValue(
                    UiState.Error(
                        e.message ?: "Something went wrong"
                    )
                )
            }
        }
    }


    class ProductListVMFactory(
        private val repository: ShopRepository
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(
            modelClass: Class<T>
        ): T {

            return ProductListViewModel(repository) as T
        }
    }
}
