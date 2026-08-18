package com.code4galaxy.ecommerceapp.view

import android.os.Bundle
import android.text.InputType
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.code4galaxy.ecommerceapp.UiState
import com.code4galaxy.ecommerceapp.databinding.FragmentRegisterBinding
import com.code4galaxy.ecommerceapp.model.remote.RetrofitBuilder
import com.code4galaxy.ecommerceapp.repository.AuthRepositoryImpl
import com.code4galaxy.ecommerceapp.viewmodel.AuthVMFactory
import com.code4galaxy.ecommerceapp.viewmodel.AuthViewModel

class RegisterFragment : Fragment() {
    private var isPasswordVisible = false
    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: AuthViewModel by viewModels {
        AuthVMFactory(AuthRepositoryImpl(RetrofitBuilder.apiService))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpClicks()
        observeRegister()
    }

    private fun observeRegister() {
        viewModel.registerState.observe(viewLifecycleOwner){
            state ->
            when(state){
                UiState.Loading->{
                    binding.registerbtn.isEnabled=false
                }

                is UiState.Success ->{
                    binding.registerbtn.isEnabled=true
                    val registerResponse = state.data
                    Toast.makeText(requireContext(),registerResponse.message, Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
                is UiState.Error->{
                    binding.registerbtn.isEnabled=true
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                }
                else -> Unit
            }

        }
    }

    private fun setUpClicks() {
        binding.registerbtn.setOnClickListener {
            val fullName = binding.registeredtname.text.toString().trim()
            val mobileNo = binding.registeredtmobile.text.toString().trim()
            val email= binding.registeredtemail.text.toString().trim()
            val password=binding.registeredtpassword.text.toString().trim()
            val mobileRegex = Regex("^\\d{8,}$")
            val passwordRegex= Regex("^(?=.*[A-Z])(?=.*[^A-Za-z0-9]).{8,}\$")
            if(fullName.isEmpty()){
                binding.registeredtname.error="Enter Full Name"
                return@setOnClickListener
            }
            if(mobileNo.isEmpty()){
                binding.registeredtmobile.error="Enter MobileNo."
                return@setOnClickListener
            }
            if(!mobileNo.matches(mobileRegex)){
                binding.registeredtmobile.error = "Mobile number must contain at least 8 digits"

                return@setOnClickListener
            }
            if(email.isEmpty()){
                binding.registeredtemail.error="Enter Email"
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

                binding.registeredtemail.error =
                    "Enter valid email"

                return@setOnClickListener
            }

            if(password.isEmpty()){
                binding.registeredtpassword.error="Enter Password"
                return@setOnClickListener

            }
            if (!password.matches(passwordRegex)) {
                binding.registeredtpassword.error = "Password must have 8 characters, 1 uppercase and 1 special character"
                return@setOnClickListener
            }
            viewModel.register(fullName,mobileNo,email,password)
        }
        binding.registertevhaveaccount.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.imgShowPassword.setOnClickListener {
            isPasswordVisible =!isPasswordVisible
            if(isPasswordVisible){
                binding.registeredtpassword.inputType=
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD

            }
            else{
                binding.registeredtpassword.inputType=
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            binding.registeredtpassword.setSelection(
                binding.registeredtpassword.text?.length ?: 0
            )
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
    }
}