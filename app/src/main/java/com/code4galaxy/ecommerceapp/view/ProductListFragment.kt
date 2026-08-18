package com.code4galaxy.ecommerceapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.code4galaxy.ecommerceapp.R
import com.code4galaxy.ecommerceapp.UiState
import com.code4galaxy.ecommerceapp.adapters.ProductAdapter
import com.code4galaxy.ecommerceapp.databinding.FragmentProductListBinding
import com.code4galaxy.ecommerceapp.model.local.CartDatabase
import com.code4galaxy.ecommerceapp.model.local.CartItem
import com.code4galaxy.ecommerceapp.model.remote.RetrofitBuilder
import com.code4galaxy.ecommerceapp.repository.CartRepositoryImpl
import com.code4galaxy.ecommerceapp.repository.ShopRepositoryImpl
import com.code4galaxy.ecommerceapp.response.Subcategory
import com.code4galaxy.ecommerceapp.utils.SessionManager
import com.code4galaxy.ecommerceapp.utils.hide
import com.code4galaxy.ecommerceapp.utils.show
import com.code4galaxy.ecommerceapp.viewmodel.CartViewModel
import com.code4galaxy.ecommerceapp.viewmodel.ProductListViewModel
import com.google.android.material.tabs.TabLayout

class ProductListFragment : Fragment() {
    private lateinit var binding: FragmentProductListBinding
    private lateinit var adapter: ProductAdapter
    private lateinit var sessionManager: SessionManager
    private val cartViewModel: CartViewModel by viewModels {
        CartViewModel.CartVMFactory(CartRepositoryImpl(CartDatabase
            .getDatabase(requireContext())
            .cartDao()))
    }
    private val viewModel : ProductListViewModel by viewModels {
        ProductListViewModel.ProductListVMFactory(
            ShopRepositoryImpl(RetrofitBuilder.apiService)
        )
    }

    private var categoryId : String ?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductListBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = SessionManager(requireContext())
        setupRecylerView()
        getCategoryId()
        observeSubcategories()
        observeProducts()
        observeCart()
    }

    private fun observeCart() {
        val userId = sessionManager.getUserId() ?: return
        cartViewModel.getActiveCart(userId).observe(viewLifecycleOwner){cart ->
            if(cart != null){
                cartViewModel.getCartItems(cart.cartId)
                    .observe(viewLifecycleOwner){
                        cartItems -> adapter.updateCartItems(cartItems)
                    }
            }else{
                adapter.updateCartItems(emptyList())
            }
        }
    }

    private fun observeProducts() {
        viewModel.productState.observe(viewLifecycleOwner){state ->
            when(state){
                UiState.Loading->{
                    binding.productlistprogressbar.show()
                }
                is UiState.Success->{
                    adapter.submitList(state.data.products)
                    binding.productlistprogressbar.hide()
                }
                is UiState.Error ->{
                    Toast.makeText(requireContext(),state.message, Toast.LENGTH_SHORT).show()
                    binding.productlistprogressbar.hide()
                }else -> Unit
            }

        }
    }

    private fun observeSubcategories() {
        viewModel.subCategory.observe(viewLifecycleOwner){state ->
            when(state){
                UiState.Loading->{
                    binding.productlistprogressbar.show()
                }
                is UiState.Success->{
                    val subCategories = state.data.subcategories
                    binding.subCategoryTabLayout.removeAllTabs()
                    subCategories.forEach { 
                        subcategory -> 
                        val tab = binding.subCategoryTabLayout
                            .newTab()
                            .setText(subcategory.subcategoryName)
                        binding.subCategoryTabLayout.addTab(tab)
                        binding.productlistprogressbar.hide()
                    }
                    setupTabListener(subCategories)
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

    private fun setupTabListener(subCategories: List<Subcategory>) {
        binding.subCategoryTabLayout.addOnTabSelectedListener(
            object: TabLayout.OnTabSelectedListener{
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    val position = tab ?.position ?:return
                    val selectedSubCategory =
                        subCategories[position]

                    viewModel.getSubProducts(
                        selectedSubCategory.subcategoryId
                    )
                }
                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                }
            }
        )
        if(subCategories.isNotEmpty()){
            viewModel.getSubProducts(subCategories[0].subcategoryId)
        }
    }

    private fun getCategoryId() {
        categoryId = arguments?.getString("categoryId")
        categoryId?.let {
            viewModel.getSubCategories(it)
        }

    }

    private fun setupRecylerView() {
        adapter= ProductAdapter(
            onProductClick = { product ->
                val bundle = Bundle().apply {
                    putString("productId",product.productId)
                    putString("categoryId",product.categoryId)
                }
                findNavController().navigate(R.id.action_productListFragment_to_productDetailsFragment,bundle)
            },
            onAddToCart = { product ->
               val userId = sessionManager.getUserId()
                if(userId!=null){
                    cartViewModel.addToCart(
                        userId = userId,
                        productId = product.productId,
                        productName = product.productName,
                        description = product.description,
                        price = product.price.toDoubleOrNull()?:0.0,
                        imageUrl = product.productImageUrl

                    )
                }
            },
            onIncrease = { cartItem ->
                cartViewModel.increaseQuantity(cartItem)
            },
            onDecrease = { cartItem ->
                cartViewModel.decreaseQuantity(cartItem)
            }
        )
        binding.productRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.productRecyclerView.adapter = adapter
    }
}