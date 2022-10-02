package com.example.newrecipie.view.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.newrecipie.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavBar = findViewById<BottomNavigationView>(R.id.bottom_nav)
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        NavigationUI.setupWithNavController(bottomNavBar, navController)


        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.categoryItemsFragment) {
                bottomNavBar.visibility = View.GONE
            } else if (destination.id == R.id.mealDetailFragment) {
                bottomNavBar.visibility = View.GONE
            } else {
                bottomNavBar.visibility = View.VISIBLE
            }
        }

    }


    override fun onBackPressed() {
        super.onBackPressed()
    }
}