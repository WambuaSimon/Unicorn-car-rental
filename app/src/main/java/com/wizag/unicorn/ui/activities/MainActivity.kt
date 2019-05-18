package com.wizag.unicorn.ui.activities

import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.wizag.unicorn.R
import com.wizag.unicorn.ui.fragments.BookingsFragment
import com.wizag.unicorn.ui.fragments.HomeFragment
import com.wizag.unicorn.ui.fragments.ProfileFragment

class MainActivity : AppCompatActivity() {



    private val mOnNavigationItemSelectedListener = object : BottomNavigationView.OnNavigationItemSelectedListener {

        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.navigation_home -> {

                    val fragment = HomeFragment.Companion.newInstance()
                    addFragment(fragment)

                    return true
                }
                R.id.navigation_profile -> {
                    val fragment = ProfileFragment()
                    addFragment(fragment)
                    return true
                }
                R.id.navigation_bookings -> {
                    var fragment = BookingsFragment()
                    addFragment(fragment)
                    return true
                }
            }
            return false
        }

    }

    /**
     * add/replace fragment in container [framelayout]
     */
    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
            .replace(R.id.content, fragment, fragment.javaClass.getSimpleName())
            .addToBackStack(fragment.javaClass.getSimpleName())
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val navigation = findViewById(R.id.navigation) as BottomNavigationView
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)


        val fragment = HomeFragment.Companion.newInstance()
        addFragment(fragment)
    }

}