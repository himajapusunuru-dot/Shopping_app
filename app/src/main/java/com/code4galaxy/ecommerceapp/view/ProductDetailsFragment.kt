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
import com.code4galaxy.ecommerceapp.model.local.CartDatabase
import com.code4galaxy.ecommerceapp.model.remote.RetrofitBuilder
import com.code4galaxy.ecommerceapp.repository.CartRepositoryImpl
import com.code4galaxy.ecommerceapp.repository.ShopRepositoryImpl
import com.code4galaxy.ecommerceapp.utils.SessionManager
import com.code4galaxy.ecommerceapp.utils.hide
import com.code4galaxy.ecommerceapp.utils.show
import com.code4galaxy.ecommerceapp.viewmodel.CartViewModel
import com.code4galaxy.ecommerceapp.viewmodel.ProductDetailsViewModel

class ProductDetailsFragment : Fragment() {

    private lateinit var binding: FragmentProductDetailsBinding
    private lateinit var reviewAdapter: ReviewAdapter
    private lateinit var specificationAdapter: SpecificationAdapter
    private lateinit var productImageAdapter: ProductImagePagerAdapter
    private lateinit var sessionManager: SessionManager

    private val viewModel: ProductDetailsViewModel by viewModels {
        ProductDetailsViewModel.ProductDetailsVMFactory(
            ShopRepositoryImpl(
                RetrofitBuilder.apiService
            )
        )
    }

    private val cartViewModel: CartViewModel by viewModels {
        CartViewModel.CartVMFactory(
            CartRepositoryImpl(
                CartDatabase
                    .getDatabase(requireContext())
                    .cartDao()
            )
        )
    }

    private var productId: String? = null
    private var categoryId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentProductDetailsBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        sessionManager = SessionManager(requireContext())

        setupReviewRv()
        setupSpecificationRv()
        observeProductDetails()
        observeCartState()
        getProductId()
    }

    private fun observeProductDetails() {

        viewModel.productDetailsState.observe(viewLifecycleOwner) { state ->

            when (state) {

                UiState.Loading -> {
                    binding.progressbarProductdetails.show()
                }

                is UiState.Success -> {

                    binding.progressbarProductdetails.hide()

                    val product = state.data.product
                    observeProductCart(product.productId)

                    binding.productDetailsName.text =
                        product.productName

                    binding.productDetailsDescription.text =
                        product.description

                    binding.productDetailsPrice.text =
                        "$ ${product.price}"

                    binding.productRating.rating =
                        product.averageRating.toFloatOrNull() ?: 0f

                    specificationAdapter.submitList(
                        product.specifications
                    )

                    reviewAdapter.submitList(
                        product.reviews
                    )

                    val images =
                        product.images.map { it.image }

                    productImageAdapter =
                        ProductImagePagerAdapter(
                            this,
                            images
                        )

                    binding.productDetailsImage.adapter =
                        productImageAdapter

                    binding.productDetailsAddToCart
                        .setOnClickListener {

                            val userId =
                                sessionManager.getUserId()
                                    ?: return@setOnClickListener

                            cartViewModel.addToCart(
                                userId = userId,
                                productId = product.productId,
                                productName = product.productName,
                                description = product.description,
                                price = product.price.toDoubleOrNull()
                                    ?: 0.0,
                                imageUrl = product.productImageUrl
                            )
                        }
                }

                is UiState.Error -> {

                    binding.progressbarProductdetails.hide()

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

    private fun observeCartState() {

        cartViewModel.cartState.observe(viewLifecycleOwner) { state ->

            when (state) {

                UiState.Loading -> {
                   binding.progressbarProductdetails.show()
                }

                is UiState.Success -> {
                    binding.progressbarProductdetails.hide()

                    Toast.makeText(
                        requireContext(),
                        "Added to cart",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is UiState.Error -> {
                    binding.progressbarProductdetails.hide()

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
    private fun observeProductCart(productId: String){
        val userId = sessionManager.getUserId()?:return
        cartViewModel.getActiveCart(userId).observe(viewLifecycleOwner){
            cart ->
            if(cart == null){
                binding.productDetailsAddToCart.show()
                binding.productDetailsQuantityContainer.hide()
                return@observe
            }
            cartViewModel.getCartItems(cart.cartId).observe(viewLifecycleOwner){cartItems ->
                val cartItem = cartItems.find { it.productId == productId }
                if(cartItem!=null){
                    binding.productDetailsAddToCart.hide()
                    binding.productDetailsQuantityContainer.show()
                    binding.quantityText.text =
                        cartItem.quantity.toString()

                    binding.btnIncrease.setOnClickListener {
                        cartViewModel.increaseQuantity(cartItem)
                    }

                    binding.btnDecrease.setOnClickListener {
                        cartViewModel.decreaseQuantity(cartItem)
                    }
                }
                else {

                    binding.productDetailsAddToCart.show()
                    binding.productDetailsQuantityContainer.hide()
                }
            }
        }
    }

    private fun setupSpecificationRv() {

        specificationAdapter =
            SpecificationAdapter()

        binding.specificationsContainer.layoutManager =
            LinearLayoutManager(requireContext())

        binding.specificationsContainer.adapter =
            specificationAdapter
    }

    private fun setupReviewRv() {

        reviewAdapter =
            ReviewAdapter()

        binding.reviewsRecyclerView.layoutManager =
            LinearLayoutManager(requireContext())

        binding.reviewsRecyclerView.adapter =
            reviewAdapter
    }

    private fun getProductId() {

        productId =
            arguments?.getString("productId")

        categoryId =
            arguments?.getString("categoryId")

        productId?.let {
            viewModel.getProductDetails(it)
        }
    }
}