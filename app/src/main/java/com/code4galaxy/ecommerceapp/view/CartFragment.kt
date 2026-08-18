package com.code4galaxy.ecommerceapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.code4galaxy.ecommerceapp.R
import com.code4galaxy.ecommerceapp.adapters.CartAdapter
import com.code4galaxy.ecommerceapp.databinding.FragmentCartBinding
import com.code4galaxy.ecommerceapp.model.local.CartDatabase
import com.code4galaxy.ecommerceapp.repository.CartRepositoryImpl
import com.code4galaxy.ecommerceapp.utils.SessionManager
import com.code4galaxy.ecommerceapp.viewmodel.CartViewModel

class CartFragment : Fragment()
{
    private lateinit var binding: FragmentCartBinding
    private lateinit var adapter: CartAdapter
    private lateinit var sessionManager: SessionManager
    private val viewModel : CartViewModel by viewModels {
        CartViewModel.CartVMFactory(CartRepositoryImpl(CartDatabase
            .getDatabase(requireContext())
            .cartDao()
        ))

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentCartBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = SessionManager(requireContext())
        setUpRecylerView()
        getCartDetails()
        binding.checkoutbtn.setOnClickListener {
            findNavController().navigate(R.id.action_cartFragment_to_checkoutFragment)
        }
    }

    private fun getCartDetails() {
        val userId = sessionManager.getUserId()
        userId?.let {
            viewModel.getActiveCart(userId).observe(viewLifecycleOwner) { cart ->
                if (cart != null) {
                    observeCartItems(cart.cartId)
                } else {
                    adapter.submitList(emptyList())
                    binding.tvTotalBillAmount.text = "$0.0"
                }
            }
        }
    }

    private fun observeCartItems(cartId: Int) {
        viewModel.getCartItems(cartId).observe(viewLifecycleOwner){
            cartItems ->
            adapter.submitList(cartItems)
            val totalBill = cartItems.sumOf { cartItem ->
                cartItem.price*cartItem.quantity
            }
            binding.tvTotalBillAmount.text = "$ $totalBill"
        }
    }

    private fun setUpRecylerView() {
        adapter = CartAdapter(
            onIncrease = { cartItem ->
                viewModel.increaseQuantity(cartItem)
            },
            onDecrease = { cartItem ->
                viewModel.decreaseQuantity(cartItem)
            }
        )

        binding.cartRecyclerView.layoutManager =
            LinearLayoutManager(requireContext())

        binding.cartRecyclerView.adapter =adapter
    }


}