package com.code4galaxy.ecommerceapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.code4galaxy.ecommerceapp.adapters.CheckoutPagerAdapter
import com.code4galaxy.ecommerceapp.databinding.FragmentCheckoutBinding
import com.google.android.material.tabs.TabLayoutMediator

class CheckoutFragment : Fragment() {
    private lateinit var binding: FragmentCheckoutBinding
    private lateinit var checkoutPagerAdapter: CheckoutPagerAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCheckoutBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
    }

    private fun setupViewPager() {
        checkoutPagerAdapter = CheckoutPagerAdapter(this)
        binding.checkoutViewPager.adapter = checkoutPagerAdapter
        TabLayoutMediator(
            binding.checkoutTabLayout,
            binding.checkoutViewPager
        ){
            tab,postion ->
            tab.text = when(postion){
                0->"Cart Items"
                1->"Delivery"
                2->"Payment"
                3->"Summary"
                else -> ""
            }
        }.attach()
    }

    fun moveToPage(position: Int) {
        binding.checkoutViewPager.currentItem = position
    }


}