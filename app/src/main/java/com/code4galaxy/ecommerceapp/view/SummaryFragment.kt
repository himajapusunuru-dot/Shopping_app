package com.code4galaxy.ecommerceapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import com.code4galaxy.ecommerceapp.R
import com.code4galaxy.ecommerceapp.UiState
import com.code4galaxy.ecommerceapp.adapters.CheckoutCartAdapter
import com.code4galaxy.ecommerceapp.databinding.FragmentSummaryBinding
import com.code4galaxy.ecommerceapp.model.local.CartDatabase
import com.code4galaxy.ecommerceapp.model.local.CartItem
import com.code4galaxy.ecommerceapp.model.remote.RetrofitBuilder
import com.code4galaxy.ecommerceapp.repository.CartRepositoryImpl
import com.code4galaxy.ecommerceapp.repository.OrderRepositoryImpl
import com.code4galaxy.ecommerceapp.request.PlaceOrderRequest
import com.code4galaxy.ecommerceapp.response.Address
import com.code4galaxy.ecommerceapp.response.OrderAddress
import com.code4galaxy.ecommerceapp.response.OrderItem
import com.code4galaxy.ecommerceapp.utils.SessionManager
import com.code4galaxy.ecommerceapp.utils.hide
import com.code4galaxy.ecommerceapp.utils.show
import com.code4galaxy.ecommerceapp.viewmodel.CartViewModel
import com.code4galaxy.ecommerceapp.viewmodel.OrderViewModel
import com.code4galaxy.ecommerceapp.viewmodel.SharedCheckoutViewmodel

class SummaryFragment : Fragment() {

    private lateinit var binding: FragmentSummaryBinding
    private lateinit var adapter: CheckoutCartAdapter
    private lateinit var sessionManager: SessionManager

    private var cartItems: List<CartItem> = emptyList()
    private var totalBill: Double = 0.0

    private var selectedAddress: Address? = null
    private var selectedPaymentMethod: String? = null
    private var activeCartId: Int? = null

    private val sharedCheckoutViewmodel: SharedCheckoutViewmodel by viewModels(
        ownerProducer = { requireParentFragment() }
    )

    private val cartViewModel: CartViewModel by viewModels {
        CartViewModel.CartVMFactory(
            CartRepositoryImpl(
                CartDatabase.getDatabase(requireContext()).cartDao()
            )
        )
    }

    private val orderViewModel: OrderViewModel by viewModels {
        OrderViewModel.OrderVMFactory(
            OrderRepositoryImpl(
                RetrofitBuilder.apiService
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSummaryBinding.inflate(
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

        setupRecyclerView()

        getCartDetails()

        observeSelectedAddress()

        observePaymentMethod()

        observePlaceOrder()

        binding.confirmOrderButton.setOnClickListener {
            placeOrder()
        }
    }

    private fun placeOrder() {

        val userId =
            sessionManager.getUserId()
                ?.toIntOrNull()
                ?: return

        val address = selectedAddress
        val paymentMethod = selectedPaymentMethod

        if (address == null) {

            Toast.makeText(
                requireContext(),
                "Please select a delivery address",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        if (paymentMethod == null) {

            Toast.makeText(
                requireContext(),
                "Please select a payment method",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        if (cartItems.isEmpty()) {

            Toast.makeText(
                requireContext(),
                "Cart is Empty",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        val orderItems = cartItems.map { cartItem ->

            OrderItem(
                productId = cartItem.productId.toInt(),
                quantity = cartItem.quantity,
                unitPrice = cartItem.price
            )
        }

        val orderAddress = OrderAddress(
            title = address.title,
            address = address.address
        )

        val request = PlaceOrderRequest(
            userId = userId,
            deliveryAddress = orderAddress,
            items = orderItems,
            billAmount = totalBill,
            paymentMethod = paymentMethod
        )

        orderViewModel.placeOrder(request)
    }

    private fun observePlaceOrder() {

        orderViewModel.placeOrderState.observe(
            viewLifecycleOwner
        ) { state ->

            when (state) {

                UiState.Loading -> {

                }

                is UiState.Success -> {

                    activeCartId?.let { cartId ->
                        cartViewModel.clearCartItems(cartId)
                    }

                    state.data.orderId?.let { orderId ->
                        sessionManager.saveLatestOrderId(orderId)

                        val bundle = Bundle().apply {
                            putInt("orderId", orderId)
                        }

                        parentFragment
                            ?.findNavController()
                            ?.navigate(
                                R.id.ordersFragment,
                                bundle,
                                navOptions {
                                    popUpTo(
                                        R.id.checkoutFragment
                                    ) {
                                        inclusive = true
                                    }
                                }
                            )
                    }
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

    private fun observePaymentMethod() {

        sharedCheckoutViewmodel.paymentMethod.observe(
            viewLifecycleOwner
        ) { paymentMethod ->
            paymentMethod?.let {
                selectedPaymentMethod = it

                binding.paymentOptionValue.text =
                    when (it) {

                        "COD" ->
                            "Cash On Delivery"

                        "INTERNET_BANKING" ->
                            "Internet Banking"

                        "CARD" ->
                            "Debit Card / Credit Card"

                        "PAYPAL" ->
                            "PayPal"

                        else ->
                            it
                    }
            }
        }
    }

    private fun observeSelectedAddress() {

        sharedCheckoutViewmodel.selectedAddress.observe(
            viewLifecycleOwner
        ) { address ->
            address?.let {
                selectedAddress = it

                binding.addressTitle.text =
                    it.title

                binding.addressText.text =
                    it.address
            }
        }
    }

    private fun getCartDetails() {

        val userId =
            sessionManager.getUserId()
                ?: return

        cartViewModel.getActiveCart(userId)
            .observe(viewLifecycleOwner) { cart ->

                if (cart != null) {
                    activeCartId = cart.cartId
                    observeCartItems(cart.cartId)
                }
            }
    }

    private fun observeCartItems(cartId: Int) {

        cartViewModel.getCartItems(cartId)
            .observe(viewLifecycleOwner) { items ->

                cartItems = items

                adapter.submitList(items)

                totalBill = items.sumOf {
                    it.price * it.quantity
                }

                binding.totalBillAmount.text =
                    "$ $totalBill"
            }
    }

    private fun setupRecyclerView() {

        adapter = CheckoutCartAdapter()

        binding.summaryCartRecyclerView.layoutManager =
            LinearLayoutManager(requireContext())

        binding.summaryCartRecyclerView.adapter =
            adapter
    }
}