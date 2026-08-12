package com.code4galaxy.ecommerceapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.code4galaxy.ecommerceapp.repository.ShopRepository

class CategoryVMFactory(val repo : ShopRepository): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CategoryViewModel(repo)as T
    }
}