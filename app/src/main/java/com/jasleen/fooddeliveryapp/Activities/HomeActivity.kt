package com.jasleen.fooddeliveryapp.Activities

// add forgot password functionality
// add logout functionality

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View.inflate
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.res.ColorStateListInflaterCompat.inflate
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationView
import com.jasleen.fooddeliveryapp.R

class HomeActivity : AppCompatActivity() {


    lateinit var coordinatorLayout : CoordinatorLayout
//    lateinit var toolbar : androidx.appcompat.widget.Toolbar
    lateinit var frameLayout: FrameLayout
    lateinit var nav_view: NavigationView
    lateinit var drawer_layout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        coordinatorLayout = findViewById(R.id.coordinatorLayout)
//        toolbar = findViewById(R.id.toolbar)
        frameLayout = findViewById(R.id.frame)
        nav_view = findViewById(R.id.nav_view)
        drawer_layout = findViewById(R.id.drawer_layout)

        val navController = Navigation.findNavController(this, R.id.fragment)
        NavigationUI.setupWithNavController(nav_view, navController)
        NavigationUI.setupActionBarWithNavController(this, navController, drawer_layout)

    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(Navigation.findNavController(this, R.id.fragment), drawer_layout)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }

}