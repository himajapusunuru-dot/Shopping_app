package com.code4galaxy.ecommerceapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.code4galaxy.ecommerceapp.UiState
import com.code4galaxy.ecommerceapp.adapters.ProductImagePagerAdapter
import com.code4galaxy.ecommerceapp.adapters.ReviewAdapter
import com.code4galaxy.ecommerceapp.adapters.SpecificationAdapter
import com.code4galaxy.ecommerceapp.databinding.FragmentProductDetailsBinding
import com.code4galaxy.ecommerceapp.remote.RetrofitBuilder
import com.code4galaxy.ecommerceapp.repository.ShopRepositoryImpl
import com.code4galaxy.ecommerceapp.utils.hide
import com.code4galaxy.ecommerceapp.utils.show
import com.code4galaxy.ecommerceapp.viewmodel.ProductDetailsViewModel

class ProductDetailsFragment: Fragment() {
    private lateinit var binding: FragmentProductDetailsBinding
    private lateinit var reviewAdapter: ReviewAdapter
    private lateinit var specificationAdapter: SpecificationAdapter
    private lateinit var productImageAdapter: ProductImagePagerAdapter
    private val viewModel : ProductDetailsViewModel by viewModels {
        ProductDetailsViewModel.ProductDetailsVMFactory(ShopRepositoryImpl(RetrofitBuilder.apiService))
    }
    private var productId : String ?=null
    private var categoryId : String ?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentProductDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupReviewRv()
        setupSpecificationRv()
        observeProductDetails()
        getProductId()
    }

    private fun observeProductDetails() {
        viewModel.productDetailsState.observe(viewLifecycleOwner){state ->
            when(state){
                UiState.Loading ->{
                    binding.progressbarProductdetails.show()
                }

                is UiState.Success ->{
                    val product = state.data.product
                    binding.progressbarProductdetails.hide()
                    binding.productDetailsName.text = product.productName
                    binding.productDetailsDescription.text =
                        product.description

                    binding.productDetailsPrice.text =
                        "$ ${product.price}"

                    binding.productRating.rating =
                        product.averageRating.toFloatOrNull() ?: 0f
                    specificationAdapter.submitList(product.specifications)
                    reviewAdapter.submitList(product.reviews)
                    Toast.makeText(
                        requireContext(),
                        "Images count = ${product.images.size}",
                        Toast.LENGTH_LONG
                    ).show()
                    val images = product.images.map { it.image }
                    productImageAdapter= ProductImagePagerAdapter(this,images)
                    binding.productDetailsImage.adapter = productImageAdapter
                }
                is UiState.Error ->{
                    binding.progressbarProductdetails.hide()
                }
                else -> Unit

            }
        }
    }

    private fun setupSpecificationRv() {
        specificationAdapter = SpecificationAdapter()
        binding.specificationsContainer.layoutManager = LinearLayoutManager(requireContext())
        binding.specificationsContainer.adapter = specificationAdapter
    }

    private fun setupReviewRv() {
        reviewAdapter = ReviewAdapter()
        binding.reviewsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.reviewsRecyclerView.adapter = reviewAdapter
    }

    private fun getProductId() {
        productId = arguments?.getString("productId")
        categoryId = arguments?.getString("categoryId")
        productId?.let { viewModel.getProductDetails(it) }
    }
}