package com.code4galaxy.ecommerceapp

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.code4galaxy.ecommerceapp.utils.hide
import com.code4galaxy.ecommerceapp.utils.show
import com.code4galaxy.ecommerceapp.view.HomeFragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var toolbar: MaterialToolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var navigationView: NavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeViews()
        setupToolbar()
        setupNavController()
        setupBottomNavigation()
        setupSideNavigation()
        setupDestinationListener()

    }

    private fun setupToolbar() {
        toolbar.inflateMenu(R.menu.toolbar_menu)

        toolbar.setNavigationOnClickListener {

            when (navController.currentDestination?.id) {

                R.id.homeFragment -> {
                    drawerLayout.openDrawer(GravityCompat.START)
                }

                else -> {
                    navController.navigateUp()
                }
            }
        }

    }

    private fun setupSideNavigation() {
        navigationView.setNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.logout ->{
                    drawerLayout.closeDrawer(GravityCompat.START)
                    navController.navigate(R.id.loginFragment)
                    true
                }
                else -> false
            }

        }
    }

    private fun setupBottomNavigation() {

        bottomNavigationView.setOnItemSelectedListener { item ->

            when (item.itemId) {

                R.id.homeFragment -> {
                    navController.navigate(R.id.homeFragment)
                    true
                }

                R.id.cartFragment -> {
                    navController.navigate(R.id.cartFragment)
                    true
                }

                R.id.profileFragment -> {
                    navController.navigate(R.id.profileFragment)
                    true
                }

                else -> false
            }
        }
    }

    private fun initializeViews() {

        toolbar =
            findViewById(R.id.titleMaterialToolBar)

        drawerLayout =
            findViewById(R.id.drawerLayout)

        bottomNavigationView =
            findViewById(R.id.bottomNavigationView)

        navigationView =
            findViewById(R.id.navigationView)
    }

    private fun setupNavController() {

        val navHostFragment =
            supportFragmentManager
                .findFragmentById(R.id.mainFragmentContainer) as NavHostFragment

        navController =
            navHostFragment.navController
    }

    private fun setupDestinationListener() {

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id){
                R.id.loginFragment -> {
                    toolbar.title ="Login"
                    toolbar.navigationIcon = null
                    bottomNavigationView.hide()
                    toolbar.menu.findItem(R.id.searchbtn).isVisible = false
                }
                R.id.registerFragment -> {

                    toolbar.title = "REGISTER"

                    toolbar.navigationIcon = null

                    toolbar.menu.findItem(R.id.searchbtn)
                        .isVisible = false
                    bottomNavigationView.hide()

                }
                R.id.homeFragment ->{
                    toolbar.title = "Super Cart"
                    toolbar.setNavigationIcon(R.drawable.menu)
                    toolbar.menu.findItem(R.id.searchbtn).isVisible = true
                    bottomNavigationView.show()

                }
                R.id.productListFragment ->{
                    toolbar.title = "PRODUCTS"
                    toolbar.setNavigationIcon(R.drawable.back_arrow)
                    toolbar.menu.findItem(R.id.searchbtn).isVisible = false
                    bottomNavigationView.show()
                }
            }
        }
    }


}