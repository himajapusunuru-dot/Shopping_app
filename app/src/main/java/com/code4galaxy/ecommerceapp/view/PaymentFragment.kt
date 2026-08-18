package com.code4galaxy.ecommerceapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.code4galaxy.ecommerceapp.databinding.FragmentPaymentBinding
import com.code4galaxy.ecommerceapp.viewmodel.SharedCheckoutViewmodel

class PaymentFragment : Fragment() {

    private lateinit var binding: FragmentPaymentBinding

    private var selectedPaymentMethod: String? = null

    private val sharedCheckoutViewModel: SharedCheckoutViewmodel by viewModels(
        ownerProducer = { requireParentFragment() }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentPaymentBinding.inflate(
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

        setupPaymentSelection()
        setupNextButton()
    }

    private fun setupPaymentSelection() {

        // add your radio button listeners here
        binding.codRadioButton.setOnClickListener {
            selectedPaymentMethod = "COD"
            sharedCheckoutViewModel.setPaymentMethod("COD")
        }
        binding.internetBankingRadioButton.setOnClickListener {
            selectedPaymentMethod = "INTERNET_BANKING"
            sharedCheckoutViewModel.setPaymentMethod("INTERNET_BANKING")
        }
        binding.cardRadioButton.setOnClickListener {
            selectedPaymentMethod = "CARD"
            sharedCheckoutViewModel.setPaymentMethod("CARD")
        }
        binding.paypalRadioButton.setOnClickListener {

            selectedPaymentMethod = "PAYPAL"

            sharedCheckoutViewModel.setPaymentMethod("PAYPAL")
        }
    }

    private fun setupNextButton() {

        binding.nextButton.setOnClickListener {

            if (selectedPaymentMethod == null) {

                Toast.makeText(
                    requireContext(),
                    "Please select a payment method",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            val checkoutFragment =
                parentFragment as? CheckoutFragment

            checkoutFragment?.moveToPage(3)
        }
    }
}