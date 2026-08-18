package com.code4galaxy.ecommerceapp

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.code4galaxy.ecommerceapp.model.remote.RetrofitBuilder
import com.code4galaxy.ecommerceapp.repository.AuthRepositoryImpl
import com.code4galaxy.ecommerceapp.request.LogoutRequest
import com.code4galaxy.ecommerceapp.utils.SessionManager
import com.code4galaxy.ecommerceapp.utils.hide
import com.code4galaxy.ecommerceapp.utils.show
import com.code4galaxy.ecommerceapp.viewmodel.AuthVMFactory
import com.code4galaxy.ecommerceapp.viewmodel.AuthViewModel
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var toolbar: MaterialToolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var navigationView: NavigationView
    private lateinit var sessionManager: SessionManager

    private val authViewModel: AuthViewModel by viewModels {
        AuthVMFactory(
            AuthRepositoryImpl(
                RetrofitBuilder.apiService
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        sessionManager = SessionManager(this)

        initializeViews()
        setupNavController()
        setupToolbar()
        setupNavigation()
        setupDestinationListener()
        setupNavHeader()
        observeLogout()
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
                .findFragmentById(
                    R.id.mainFragmentContainer
                ) as NavHostFragment

        navController =
            navHostFragment.navController
    }

    private fun setupToolbar() {

        toolbar.inflateMenu(
            R.menu.toolbar_menu
        )

        toolbar.setNavigationOnClickListener {

            when (
                navController
                    .currentDestination
                    ?.id
            ) {

                R.id.homeFragment,
                R.id.cartFragment,
                R.id.ordersFragment,
                R.id.profileFragment -> {

                    drawerLayout.openDrawer(
                        GravityCompat.START
                    )
                }

                else -> {

                    navController.navigateUp()
                }
            }
        }
    }

    private fun setupNavigation() {

        bottomNavigationView
            .setupWithNavController(
                navController
            )

        navigationView
            .setupWithNavController(
                navController
            )

        navigationView
            .setNavigationItemSelectedListener { item ->

                when (item.itemId) {

                    R.id.logout -> {

                        val email =
                            sessionManager.getEmail()

                        if (email.isNullOrEmpty()) {

                            Toast.makeText(
                                this,
                                "Unable to logout",
                                Toast.LENGTH_SHORT
                            ).show()

                            return@setNavigationItemSelectedListener false
                        }

                        authViewModel.logout(
                            LogoutRequest(
                                email
                            )
                        )

                        true
                    }

                    else -> {

                        val navigated =
                            NavigationUI
                                .onNavDestinationSelected(
                                    item,
                                    navController
                                )

                        if (navigated) {

                            drawerLayout.closeDrawer(
                                GravityCompat.START
                            )
                        }

                        navigated
                    }
                }
            }
    }

    private fun observeLogout() {

        authViewModel.logoutState.observe(
            this
        ) { state ->

            when (state) {

                UiState.Loading -> Unit

                is UiState.Success -> {

                    Toast.makeText(
                        this,
                        state.data.message,
                        Toast.LENGTH_SHORT
                    ).show()

                    drawerLayout.closeDrawer(
                        GravityCompat.START
                    )

                    sessionManager.clearSession()

                    navController.navigate(
                        R.id.loginFragment
                    ) {

                        popUpTo(
                            R.id.nav_graph
                        ) {
                            inclusive = true
                        }
                    }
                }

                is UiState.Error -> {

                    Toast.makeText(
                        this,
                        state.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else -> Unit
            }
        }
    }

    private fun setupNavHeader() {

        val headerView =
            navigationView.getHeaderView(0)

        val userName =
            headerView.findViewById<TextView>(
                R.id.navUserName
            )

        val userEmail =
            headerView.findViewById<TextView>(
                R.id.navUserEmail
            )

        val userPhone =
            headerView.findViewById<TextView>(
                R.id.navUserPhone
            )

        userName.text =
            "Welcome ${
                sessionManager.getFullName()
                    ?: "User"
            }"

        userEmail.text =
            sessionManager.getEmail()
                ?: ""

        userPhone.text =
            sessionManager.getMobileNo()
                ?: ""
    }

    private fun setupDestinationListener() {

        navController
            .addOnDestinationChangedListener {
                    _,
                    destination,
                    _ ->

                when (destination.id) {

                    R.id.loginFragment -> {

                        toolbar.title =
                            "Login"

                        toolbar.navigationIcon =
                            null

                        toolbar.menu
                            .findItem(
                                R.id.searchbtn
                            )
                            .isVisible =
                            false

                        bottomNavigationView
                            .hide()
                    }

                    R.id.registerFragment -> {

                        toolbar.title =
                            "REGISTER"

                        toolbar.navigationIcon =
                            null

                        toolbar.menu
                            .findItem(
                                R.id.searchbtn
                            )
                            .isVisible =
                            false

                        bottomNavigationView
                            .hide()
                    }

                    R.id.homeFragment -> {

                        toolbar.title =
                            "Super Cart"

                        toolbar.setNavigationIcon(
                            R.drawable.menu
                        )

                        toolbar.menu
                            .findItem(
                                R.id.searchbtn
                            )
                            .isVisible =
                            true

                        bottomNavigationView
                            .show()

                        setupNavHeader()
                    }

                    R.id.productListFragment -> {

                        toolbar.title =
                            "PRODUCTS"

                        toolbar.setNavigationIcon(
                            R.drawable.back_arrow
                        )

                        toolbar.menu
                            .findItem(
                                R.id.searchbtn
                            )
                            .isVisible =
                            false

                        bottomNavigationView
                            .show()
                    }

                    R.id.productDetailsFragment -> {

                        toolbar.title =
                            "DETAILS"

                        toolbar.setNavigationIcon(
                            R.drawable.back_arrow
                        )

                        toolbar.menu
                            .findItem(
                                R.id.searchbtn
                            )
                            .isVisible =
                            false

                        bottomNavigationView
                            .show()
                    }

                    R.id.cartFragment -> {

                        toolbar.title =
                            "CART"

                        toolbar.setNavigationIcon(
                            R.drawable.menu
                        )

                        toolbar.menu
                            .findItem(
                                R.id.searchbtn
                            )
                            .isVisible =
                            false

                        bottomNavigationView
                            .show()

                        setupNavHeader()
                    }

                    R.id.checkoutFragment -> {

                        toolbar.title =
                            "CHECKOUT"

                        toolbar.setNavigationIcon(
                            R.drawable.back_arrow
                        )

                        toolbar.menu
                            .findItem(
                                R.id.searchbtn
                            )
                            .isVisible =
                            false

                        bottomNavigationView
                            .show()
                    }

                    R.id.orderConfirmedFragment -> {

                        toolbar.title =
                            "ORDER CONFIRMED"

                        toolbar.setNavigationIcon(
                            R.drawable.back_arrow
                        )

                        toolbar.menu
                            .findItem(
                                R.id.searchbtn
                            )
                            .isVisible =
                            false

                        bottomNavigationView
                            .show()
                    }

                    R.id.profileFragment -> {

                        toolbar.title =
                            "PROFILE"

                        toolbar.setNavigationIcon(
                            R.drawable.menu
                        )

                        toolbar.menu
                            .findItem(
                                R.id.searchbtn
                            )
                            .isVisible =
                            false

                        bottomNavigationView
                            .show()

                        setupNavHeader()
                    }

                    R.id.ordersFragment -> {

                        toolbar.title =
                            "ORDERS"

                        toolbar.setNavigationIcon(
                            R.drawable.menu
                        )

                        toolbar.menu
                            .findItem(
                                R.id.searchbtn
                            )
                            .isVisible =
                            false

                        bottomNavigationView
                            .show()

                        setupNavHeader()
                    }
                }
            }
    }
}