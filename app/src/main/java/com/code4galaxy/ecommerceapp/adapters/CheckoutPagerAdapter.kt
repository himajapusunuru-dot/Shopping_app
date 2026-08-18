package com.code4galaxy.ecommerceapp.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.code4galaxy.ecommerceapp.view.CheckoutCartFragment
import com.code4galaxy.ecommerceapp.view.CheckoutFragment
import com.code4galaxy.ecommerceapp.view.DeliveryFragment
import com.code4galaxy.ecommerceapp.view.PaymentFragment
import com.code4galaxy.ecommerceapp.view.SummaryFragment

class CheckoutPagerAdapter(
    fragment: Fragment
): FragmentStateAdapter(fragment) {
    override fun createFragment(position: Int): Fragment {
        return when(position){
            0-> CheckoutCartFragment()
            1-> DeliveryFragment()
            2-> PaymentFragment()
            3-> SummaryFragment()
            else-> CheckoutCartFragment()
        }
    }

    override fun getItemCount(): Int {
        return 4

    }
}