package com.code4galaxy.ecommerceapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.code4galaxy.ecommerceapp.R
import com.code4galaxy.ecommerceapp.UiState
import com.code4galaxy.ecommerceapp.adapters.OrderConfirmedAdapter
import com.code4galaxy.ecommerceapp.databinding.FragmentOrderConfirmedBinding
import com.code4galaxy.ecommerceapp.model.remote.RetrofitBuilder
import com.code4galaxy.ecommerceapp.repository.OrderRepositoryImpl
import com.code4galaxy.ecommerceapp.viewmodel.OrderViewModel

class OrderConfirmedFragment : Fragment() {

    private lateinit var binding: FragmentOrderConfirmedBinding
    private lateinit var adapter: OrderConfirmedAdapter
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

        binding = FragmentOrderConfirmedBinding.inflate(
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

        val orderId =
            arguments?.getInt("orderId") ?: 0
        setupRecyclerView()
        observeOrderDetails()
        orderViewModel.getOrderDetails(
            orderId.toString())
    }

    private fun setupRecyclerView() {
        adapter = OrderConfirmedAdapter()

        binding.orderItemsRecyclerView.layoutManager =
            LinearLayoutManager(requireContext())

        binding.orderItemsRecyclerView.adapter =
            adapter
    }

    private fun observeOrderDetails() {

        orderViewModel.orderDetailsState.observe(
            viewLifecycleOwner
        ) { state ->

            when (state) {

                UiState.Loading -> {
                }

                is UiState.Success -> {

                    val order = state.data.order ?: return@observe

                    binding.orderIdValue.text =
                        "#${order.orderId}"

                    binding.orderStatusValue.text =
                        order.orderStatus

                    binding.totalBillAmount.text =
                        "$ ${order.billAmount}"

                    binding.addressTitle.text =
                        order.addressTitle

                    binding.addressText.text =
                        order.address

                    binding.paymentOptionValue.text =
                        when (order.paymentMethod) {
                            "COD" -> "Cash On Delivery"
                            "INTERNET_BANKING" -> "Internet Banking"
                            "CARD" -> "Debit Card / Credit Card"
                            "PAYPAL" -> "PayPal"
                            else -> order.paymentMethod
                        }
                    adapter.submitList(order.items)
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
}
