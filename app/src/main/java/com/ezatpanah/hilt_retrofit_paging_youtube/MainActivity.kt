package com.ezatpanah.hilt_retrofit_paging_youtube

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.ezatpanah.hilt_retrofit_paging_youtube.Contact.UI.ContactDialog
import com.ezatpanah.hilt_retrofit_paging_youtube.Contact.UI.ContactDialogSearch
import com.ezatpanah.hilt_retrofit_paging_youtube.Emergency.UI.EmergencyFragment
import com.ezatpanah.hilt_retrofit_paging_youtube.Home.UI.HomeFragment
import com.ezatpanah.hilt_retrofit_paging_youtube.Login.LoginPage
import com.ezatpanah.hilt_retrofit_paging_youtube.Normal.UI.NormalFragment
import com.ezatpanah.hilt_retrofit_paging_youtube.Unsaved.UI.UnsavedFragment
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var btnContact:ImageView
    private lateinit var btnContactSearch:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnContact = findViewById(R.id.btn_contact)
        btnContactSearch = findViewById(R.id.btn_contact_search)
        var drawerLayout:DrawerLayout = findViewById(R.id.drawerLayOut)
        val imageMenu: ImageView = findViewById(R.id.imageSort)
        var navView: NavigationView = findViewById(R.id.navDrawerId)
        var textTitle: TextView = findViewById(R.id.textTitle)
        navView.itemIconTintList = null
        imageMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        navView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            drawerLayout.closeDrawers()
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
//                R.id.nav_concution_supervisor -> {
//                    textTitle.text = menuItem.title
//
//                }
                R.id.nav_log_out -> {
                    Log.i("logout", "onCreate: logout now!!")
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("LOGOUT")
                    builder.setMessage("Are you sure logout")
                    builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                        Toast.makeText(
                            applicationContext,
                            android.R.string.yes, Toast.LENGTH_SHORT
                        ).show()
                        var intent = Intent(this, LoginPage::class.java)
                        startActivity(intent)
                    }

                    builder.setNegativeButton(android.R.string.no) { dialog, which ->
                        Toast.makeText(
                            applicationContext,
                            android.R.string.no, Toast.LENGTH_SHORT
                        ).show()

                    }
                    builder.show()
                }
            }

            true
        }
        textTitle.text = "บันทึกข้อมูล"
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, HomeFragment())
            .commit()

        btnContact.setOnClickListener {
            val fm = supportFragmentManager
            val myFragment = ContactDialog()
            myFragment.show(fm, "click to dialog")
        }

        btnContactSearch.setOnClickListener {
            val fm = supportFragmentManager
            val myFragment = ContactDialogSearch()
            myFragment.show(fm, "click to dialog search")
        }


    }


}