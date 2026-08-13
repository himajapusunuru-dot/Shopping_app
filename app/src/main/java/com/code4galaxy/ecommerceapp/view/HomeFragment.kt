package com.code4galaxy.ecommerceapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.code4galaxy.ecommerceapp.R
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.code4galaxy.ecommerceapp.UiState
import com.code4galaxy.ecommerceapp.adapters.CategoryAdapter
import com.code4galaxy.ecommerceapp.databinding.FragmentHomeBinding
import com.code4galaxy.ecommerceapp.remote.RetrofitBuilder
import com.code4galaxy.ecommerceapp.repository.ShopRepositoryImpl
import com.code4galaxy.ecommerceapp.utils.hide
import com.code4galaxy.ecommerceapp.utils.show
import com.code4galaxy.ecommerceapp.viewmodel.CategoryVMFactory
import com.code4galaxy.ecommerceapp.viewmodel.CategoryViewModel
import com.google.android.material.appbar.MaterialToolbar

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
        setupRecyclerView()
        observeCategories()
        showSearchContainer()
        setUpSearchClicks()
        viewModel.getCategories()
    }

    private fun setUpSearchClicks() {
        binding.imgSearch.setOnClickListener {
            val query = binding.edtsearch.text.toString().trim()
            if(query.isNotEmpty()){
                val bundle = Bundle().apply {
                    putString("searchQuery",query)
                }
                findNavController().navigate(R.id.action_homeFragment_to_productListFragment,bundle)
            }
        }
        binding.imgCloseSearch.setOnClickListener {
            binding.edtsearch.text?.clear()
            binding.searchContainer.hide()
        }
    }

    fun showSearchContainer() {
        val toolbar = requireActivity()
            .findViewById<MaterialToolbar>(R.id.titleMaterialToolBar)
        toolbar.setOnMenuItemClickListener { item ->
            when(item.itemId){
                R.id.searchbtn -> {
                    binding.searchContainer.show()
                    true
                }
                else -> false
            }
        }
        binding.imgCloseSearch.setOnClickListener {
            binding.searchContainer.hide()
            binding.edtsearch.text?.clear()
        }
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

    private fun setupRecyclerView() {
        categoryAdapter = CategoryAdapter { category ->
            val bundle = Bundle().apply {
                putString("categoryId",category.categoryId)
                putString("categoryName",category.categoryName)
            }
            findNavController().navigate(R.id.action_homeFragment_to_productListFragment,bundle)

        }
        binding.categoryRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.categoryRecyclerView.adapter = categoryAdapter
    }
    override fun onDestroyView() {
        super.onDestroyView()
    }
}