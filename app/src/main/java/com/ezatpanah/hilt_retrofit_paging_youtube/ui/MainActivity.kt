package com.ezatpanah.hilt_retrofit_paging_youtube.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.ezatpanah.hilt_retrofit_paging_youtube.R
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var drawerLayout:DrawerLayout = findViewById(R.id.drawerLayOut)
        val imageMenu: ImageView = findViewById(R.id.imageSort)
        var navView: NavigationView = findViewById(R.id.navDrawerId)
        navView.itemIconTintList = null
        imageMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        navView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            drawerLayout.closeDrawers()
            var textTitle: TextView = findViewById(R.id.textTitle)
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    textTitle.text = menuItem.title
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, HomeFragment())
                        .commit()
                }
                R.id.nav_UnSave -> {
                    textTitle.text = menuItem.title
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, UnsavedFragment())
                        .commit()
                }
                R.id.nav_emergency -> {
                    textTitle.text = menuItem.title
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, EmergencyFragment())
                        .commit()
                }
                R.id.nav_normal -> {
                    textTitle.text = menuItem.title
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, NormalFragment())
                        .commit()
                }
                R.id.nav_concution_supervisor -> {
                    textTitle.text = menuItem.title
                    Log.i("link", "onCreate: super visorpage")
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.fragment_container, NormalFragment())
//                        .commit()
                }
//                R.id.nav_log_out -> {
//                    Log.i("logout", "onCreate: logout now!!")
//                    val builder = AlertDialog.Builder(this)
//                    builder.setTitle("LOGOUT")
//                    builder.setMessage("Are you sure logout")
//                    builder.setPositiveButton(android.R.string.yes) { dialog, which ->
//                        Toast.makeText(
//                            applicationContext,
//                            android.R.string.yes, Toast.LENGTH_SHORT
//                        ).show()
//                        var intent = Intent(this, ChooseServices::class.java)
//                        startActivity(intent)
//                    }
//
//                    builder.setNegativeButton(android.R.string.no) { dialog, which ->
//                        Toast.makeText(
//                            applicationContext,
//                            android.R.string.no, Toast.LENGTH_SHORT
//                        ).show()
//
//                    }
//                    builder.show()
//                }

            }

            true
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, HomeFragment())
            .commit()
//        btn_contact.setOnClickListener {
//            val fm = supportFragmentManager
//            val myFragment = ContactDialog()
//            myFragment.show(fm, "click to dialog")
//        }
//
//        btn_contact_search.setOnClickListener {
//            val fm = supportFragmentManager
//            val myFragment = ContactDialogSearch()
//            //val myFragment = MainContact()
//            myFragment.show(fm, "click to dialog search")
//        }


    }


}