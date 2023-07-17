package com.example.uas_mobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.uas_mobile.databinding.ActivityMainBinding
import com.example.uas_mobile.home.HomeFragment
import com.example.uas_mobile.profile.ProfileFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())

        binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> replaceFragment(HomeFragment())
                R.id.profile -> replaceFragment(ProfileFragment())
            }
            true
        }
    }


     private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction()
        .replace(R.id.container, fragment)
        .addToBackStack(null)
        .commit()
    }

}