package com.code4galaxy.ecommerceapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.appbar.MaterialToolbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupbutton()
    }

    private fun setupbutton() {
        val toolBar = findViewById<MaterialToolbar>(R.id.titleMaterialToolBar)
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.mainFragmentContainer) as NavHostFragment
        val navController = navHostFragment.navController
        toolBar.inflateMenu(R.menu.toolbar_menu)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id){
                R.id.loginFragment -> {
                    toolBar.title ="Login"
                    toolBar.navigationIcon = null
                    toolBar.menu.findItem(R.id.searchbtn).isVisible = false
                }
                R.id.registerFragment -> {

                    toolBar.title = "REGISTER"

                    toolBar.navigationIcon = null

                    toolBar.menu.findItem(R.id.searchbtn)
                        .isVisible = false
                }
                R.id.homeFragment ->{
                    toolBar.title = "Super Cart"
                    toolBar.setNavigationIcon(R.drawable.menu)
                    toolBar.menu.findItem(R.id.searchbtn).isVisible = true
                }
            }
        }
    }

}