package com.code4galaxy.ecommerceapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.code4galaxy.ecommerceapp.databinding.ItemProductImageBinding
import com.code4galaxy.ecommerceapp.view.ProductImageFragment
import com.code4galaxy.ecommerceapp.viewholders.ImageViewHolder

class ProductImagePagerAdapter(fragment: Fragment,private val images: List<String>): FragmentStateAdapter(fragment){
    override fun createFragment(position: Int): Fragment {
        return ProductImageFragment.newInstance(images[position])
    }

    override fun getItemCount(): Int {
        return images.size
    }

}