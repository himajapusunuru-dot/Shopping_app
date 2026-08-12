package com.code4galaxy.ecommerceapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.code4galaxy.ecommerceapp.UiState
import com.code4galaxy.ecommerceapp.adapters.CategoryAdapter
import com.code4galaxy.ecommerceapp.databinding.FragmentHomeBinding
import com.code4galaxy.ecommerceapp.remote.RetrofitBuilder
import com.code4galaxy.ecommerceapp.repository.ShopRepositoryImpl
import com.code4galaxy.ecommerceapp.viewmodel.CategoryVMFactory
import com.code4galaxy.ecommerceapp.viewmodel.CategoryViewModel

class HomeFragment: Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var categoryAdapter: CategoryAdapter
    private val viewModel: CategoryViewModel by viewModels {
        CategoryVMFactory(ShopRepositoryImpl(RetrofitBuilder.apiService))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecylerView()
        observeCategories()
        viewModel.getCategories()
    }

    private fun observeCategories() {
        viewModel.categoryState.observe(viewLifecycleOwner){
            state ->
            when(state){
                UiState.Loading->{
                    binding.progressBar.visibility = View.VISIBLE
                }

                is UiState.Success ->{
                    binding.progressBar.visibility = View.GONE
                    categoryAdapter.submitList(state.data.categories)
                }
                is UiState.Error -> {

                    Toast.makeText(
                        requireContext(),
                        state.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else -> Unit
            }
        }
    }

    private fun setupRecylerView() {
        categoryAdapter = CategoryAdapter { category ->
            val categoryId = category.categoryId
            // Handle click
        }
        binding.categoryRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.categoryRecyclerView.adapter = categoryAdapter
    }
    override fun onDestroyView() {
        super.onDestroyView()
    }
}