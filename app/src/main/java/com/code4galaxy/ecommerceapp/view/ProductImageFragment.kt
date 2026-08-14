package com.code4galaxy.ecommerceapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.code4galaxy.ecommerceapp.R
import com.code4galaxy.ecommerceapp.databinding.FragmentProductDetailsBinding
import com.code4galaxy.ecommerceapp.databinding.FragmentProductImageBinding

class ProductImageFragment : Fragment() {
    private lateinit var binding: FragmentProductImageBinding
    private var image : String ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        image = arguments?.getString("image")
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductImageBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        image?.let {
            val imageUrl = "http://gminnovex.com/myshop/images/" + image!!.trimStart('/')
            Glide.with(this)
                .load(imageUrl)
                .error(R.drawable.error)
                .into(binding.productImage)
        }
    }

    companion object {
        fun newInstance(image: String): ProductImageFragment{
            val fragment = ProductImageFragment()
            val bundle = Bundle().apply {
                putString("image",image)
            }
            fragment.arguments = bundle
            return fragment
        }

    }
}