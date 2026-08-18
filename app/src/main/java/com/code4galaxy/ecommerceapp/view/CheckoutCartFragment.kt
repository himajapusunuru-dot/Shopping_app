package com.code4galaxy.ecommerceapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.code4galaxy.ecommerceapp.adapters.CheckoutCartAdapter
import com.code4galaxy.ecommerceapp.databinding.FragmentCheckoutCartBinding
import com.code4galaxy.ecommerceapp.model.local.CartDatabase
import com.code4galaxy.ecommerceapp.repository.CartRepositoryImpl
import com.code4galaxy.ecommerceapp.utils.SessionManager
import com.code4galaxy.ecommerceapp.viewmodel.CartViewModel

class CheckoutCartFragment : Fragment() {

    private lateinit var binding: FragmentCheckoutCartBinding
    private lateinit var adapter: CheckoutCartAdapter
    private lateinit var sessionManager: SessionManager
    private val cartViewModel : CartViewModel by viewModels {
        CartViewModel.CartVMFactory(CartRepositoryImpl(CartDatabase
            .getDatabase(requireContext())
            .cartDao())
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCheckoutCartBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = SessionManager(requireContext())
        setupRecyclerView()
        getCartDetails()
        binding.nextButton.setOnClickListener {
            val checkoutFragment = parentFragment as? CheckoutFragment
            checkoutFragment?.moveToPage(1)
        }
    }

    private fun getCartDetails() {
        val userId = sessionManager.getUserId() ?: return
        cartViewModel.getActiveCart(userId)
            .observe(viewLifecycleOwner){
                cart ->
                if(cart != null){
                    observeCartItems(cart.cartId)
                }
                else{
                    adapter.submitList(emptyList())
                    binding.tvCheckoutTotalAmount.text = "$ 0.0"
                }
            }
    }

    private fun observeCartItems(cartId: Int) {
        cartViewModel.getCartItems(cartId)
            .observe(viewLifecycleOwner){cartItems ->
                adapter.submitList(cartItems)
                val totalBill = cartItems.sumOf { cartItem ->
                    cartItem.price * cartItem.quantity
                }
                binding.tvCheckoutTotalAmount.text = "$ $totalBill"
            }
    }

    private fun setupRecyclerView() {
        adapter = CheckoutCartAdapter()
        binding.checkoutCartRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.checkoutCartRecyclerView.adapter = adapter
    }
}