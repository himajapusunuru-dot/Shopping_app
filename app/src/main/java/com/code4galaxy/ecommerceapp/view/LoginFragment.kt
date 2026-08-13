package com.code4galaxy.ecommerceapp.view

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.code4galaxy.ecommerceapp.R
import com.code4galaxy.ecommerceapp.UiState
import com.code4galaxy.ecommerceapp.databinding.FragmentLoginBinding
import com.code4galaxy.ecommerceapp.remote.RetrofitBuilder
import com.code4galaxy.ecommerceapp.repository.AuthRepositoryImpl
import com.code4galaxy.ecommerceapp.utils.SessionManager
import com.code4galaxy.ecommerceapp.viewmodel.AuthVMFactory
import com.code4galaxy.ecommerceapp.viewmodel.AuthViewModel

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var sessionManager: SessionManager
    private var isPasswordVisible = false
    private val viewModel: AuthViewModel by viewModels {
        AuthVMFactory(AuthRepositoryImpl(RetrofitBuilder.apiService))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater,container,false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = SessionManager(requireContext())
        setUpClicks()
        observeLogin()
    }

    private fun observeLogin() {
        viewModel.loginState.observe(viewLifecycleOwner){
            state ->
            when(state){
                UiState.Loading ->{
                    binding.btnLogin.isEnabled=false
                }
                is UiState.Success->{
                    binding.btnLogin.isEnabled = true
                    val user = state.data.user
                    if(user != null){
                        sessionManager.saveUser(user)
                        Toast.makeText(requireContext(),state.data.message, Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    }
                }
                is UiState.Error->{
                    binding.btnLogin.isEnabled = true
                    Toast.makeText(requireContext(),state.message, Toast.LENGTH_SHORT).show()
                }
                else -> Unit
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun setUpClicks() {
        binding.imgShowPassword.setOnClickListener {
            isPasswordVisible =!isPasswordVisible
            if(isPasswordVisible){
                binding.edttxtpasswordlogin.inputType=
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD

            }
            else{
                binding.edttxtpasswordlogin.inputType=
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            binding.edttxtpasswordlogin.setSelection(
                binding.edttxtpasswordlogin.text?.length ?: 0
            )
        }
        binding.btnLogin.setOnClickListener {
            val email = binding.edttxtemailidlogin.text.toString().trim()
            val password = binding.edttxtpasswordlogin.text.toString().trim()
            if(email.isEmpty()){
                binding.edttxtemailidlogin.error="Enter email"
                return@setOnClickListener
            }
            if(password.isEmpty()){
                binding.edttxtpasswordlogin.error="Enter password"
                return@setOnClickListener
            }
            viewModel.login(email,password)
        }
        binding.iDontHaveAccount.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }


}