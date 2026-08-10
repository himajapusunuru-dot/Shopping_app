package com.code4galaxy.ecommerceapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.appbar.MaterialToolbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment=supportFragmentManager.findFragmentById(R.id.mainFragmentContainer) as NavHostFragment
        val navController = navHostFragment.navController
        navController.addOnDestinationChangedListener { _, destination,_ ->
            when(destination.id){
                R.id.loginFragment->{
                    findViewById<MaterialToolbar>(R.id.titleMaterialToolBar).title="LOGIN"
                }
                R.id.registerFragment->{
                    findViewById<MaterialToolbar>(R.id.titleMaterialToolBar).title="Register"
                }
            }
        }


    }

}