package com.example.ekclick

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Ambil data username dari Intent
        val username = intent.getStringExtra("username")

        // Initialize the BottomNavigationView
        bottomNavigation = findViewById(R.id.bottomnav)

        // Set the default fragment on startup and pass the username
        replaceFragment(HomeFragment().apply {
            arguments = Bundle().apply {
                putString("username", username) // Kirim username ke fragment
            }
        })

        // Handle bottom navigation item selection
        bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.homeFragment -> replaceFragment(HomeFragment().apply {
                    arguments = Bundle().apply {
                        putString("username", username) // Kirim username ke fragment
                    }
                })
                R.id.userFragment -> replaceFragment(AntrianFragment())
                R.id.settingsFragment -> replaceFragment(SettingsFragment())
                else -> false
            }
            true
        }
    }

    // Function to replace fragments
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.commit()
    }
}
