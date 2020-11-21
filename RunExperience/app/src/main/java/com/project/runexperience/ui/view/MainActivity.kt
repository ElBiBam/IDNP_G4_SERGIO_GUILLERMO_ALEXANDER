package com.project.runexperience.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.project.runexperience.R

class MainActivity : AppCompatActivity() {

    lateinit var toolbar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = supportActionBar!!
        //supportActionBar?.hide()

        val bottomNavigation: BottomNavigationView = findViewById(R.id.navigationView)

        bottomNavigation.setOnNavigationItemSelectedListener {
            selectItem(it)
        }
        setFragment(LoginFragment.newInstance())
        //bottomNavigation.selectedItemId = R.id.nav_login;
        bottomNavigation.visibility = View.GONE
//        mainNav.selectedItemId = R.id.nav_profile

        //to avoid reload page
        bottomNavigation.setOnNavigationItemReselectedListener { }
    }
    public fun getNavigationView(): BottomNavigationView {
        return findViewById(R.id.navigationView)
    }

    private fun selectItem(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> { setFragment(HomeFragment.newInstance()) }
            R.id.nav_search -> { setFragment(SearchFragment.newInstance()) }
            R.id.nav_running -> { setFragment(RunningFragment.newInstance()) }
            R.id.nav_songs -> { setFragment(SongsFragment.newInstance()) }
            R.id.nav_profile -> { setFragment(ProfileFragment.newInstance()) }
        }
        return true
    }
    fun setFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainFrame, fragment, fragment.javaClass.simpleName)
            .addToBackStack(null)
            .commit()
    }
}