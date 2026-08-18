package com.code4galaxy.ecommerceapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.code4galaxy.ecommerceapp.R
import com.code4galaxy.ecommerceapp.UiState
import com.code4galaxy.ecommerceapp.adapters.AddressAdapter
import com.code4galaxy.ecommerceapp.databinding.DialogAddAddressBinding
import com.code4galaxy.ecommerceapp.databinding.FragmentDeliveryBinding
import com.code4galaxy.ecommerceapp.model.remote.RetrofitBuilder
import com.code4galaxy.ecommerceapp.repository.AddressRepositoryImpl
import com.code4galaxy.ecommerceapp.request.AddAddressRequest
import com.code4galaxy.ecommerceapp.response.Address
import com.code4galaxy.ecommerceapp.utils.SessionManager
import com.code4galaxy.ecommerceapp.utils.hide
import com.code4galaxy.ecommerceapp.utils.show
import com.code4galaxy.ecommerceapp.viewmodel.DeliveryViewModel
import com.code4galaxy.ecommerceapp.viewmodel.SharedCheckoutViewmodel

class DeliveryFragment : Fragment() {
    private lateinit var binding: FragmentDeliveryBinding
    private lateinit var adapter: AddressAdapter
    private lateinit var sessionManager: SessionManager
    private var selectedAddress : Address?=null
    private val viewModel: DeliveryViewModel by viewModels {
        DeliveryViewModel.DeliveryVMFactory(AddressRepositoryImpl(RetrofitBuilder.apiService))
    }
    private val sharedCheckoutViewModel: SharedCheckoutViewmodel by viewModels(
        ownerProducer = { requireParentFragment() }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDeliveryBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = SessionManager(requireContext())
        setupRecycleView()
        observeAddress()
        getAddress()
        observeAddAddress()
        binding.addAddressButton.setOnClickListener {
            addAddressDialog()
        }
        binding.nextButton.setOnClickListener {
            if(selectedAddress == null){
                Toast.makeText(requireContext(), "Please select an Address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
                val checkoutFragment = parentFragment as? CheckoutFragment
                checkoutFragment?.moveToPage(2)
        }
    }

    private fun observeAddress() {
        viewModel.addressListState.observe(viewLifecycleOwner){state ->
            when(state){
                UiState.Loading ->{
                    binding.deliveryProgress.show()
                }

                is UiState.Success ->{
                    binding.deliveryProgress.hide()
                    adapter.submitList(state.data.addresses)
                }
                is UiState.Error ->{
                    binding.deliveryProgress.hide()
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                }
                else -> Unit
            }
        }
    }
    private fun observeAddAddress(){
        viewModel.addAddressState.observe(viewLifecycleOwner){state ->
            when(state){
                UiState.Loading ->{
                    binding.deliveryProgress.show()
                }
                is UiState.Success -> {
                    binding.deliveryProgress.hide()
                    Toast.makeText(requireContext(), state.data.message, Toast.LENGTH_SHORT).show()
                    getAddress()
                }
                is UiState.Error -> {
                    binding.deliveryProgress.hide()
                    Toast.makeText(requireContext(),state.message, Toast.LENGTH_SHORT).show()
                }
                else -> Unit
            }
        }
    }
    private fun addAddressDialog(){
        val dialogBinding = DialogAddAddressBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .create()
        dialogBinding.saveButtn.setOnClickListener {
            val title = dialogBinding.addressTitleInput.text
                .toString().trim()
            val address = dialogBinding.addressInput.text.toString().trim()
            val userId = sessionManager.getUserId()?.toIntOrNull()?:return@setOnClickListener
            if(title.isEmpty()){
                dialogBinding.addressTitleInput.error = "Enter address title"
                return@setOnClickListener
            }
            if(address.isEmpty()){
                dialogBinding.addressInput.error = "Enter address"
                return@setOnClickListener
            }
            val request = AddAddressRequest(userId = userId,
                title = title,
                address = address)
            viewModel.addAddress(request)
            dialog.dismiss()

        }
        dialogBinding.canceladdress.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()

    }

    private fun getAddress() {
        val userId = sessionManager.getUserId()?:return
        viewModel.getAddressList(userId)
    }

    private fun setupRecycleView() {
        adapter = AddressAdapter{address -> selectedAddress = address
            sharedCheckoutViewModel.setSelectedAddress(address)
        }
        binding.addressRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.addressRecyclerView.adapter = adapter
    }
}