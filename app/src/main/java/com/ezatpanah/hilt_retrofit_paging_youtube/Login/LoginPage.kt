package com.ezatpanah.hilt_retrofit_paging_youtube.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.ezatpanah.hilt_retrofit_paging_youtube.ChooseService.UI.ChooseService
import com.ezatpanah.hilt_retrofit_paging_youtube.R

class LoginPage : AppCompatActivity() {
    val username = "test"
    val password = "test123"
    var inputUsername: EditText? = null
    var inputPassword: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)
        inputUsername = findViewById(R.id.username)
        inputPassword = findViewById(R.id.password)
        var btnLogin: Button = findViewById(R.id.btn_login)
        btnLogin.setOnClickListener {
            val intent = Intent(this, ChooseService::class.java)
            startActivity(intent)
        }
//        if (inputUsername.toString() == username && inputPassword.toString() == password) {
//            Log.i("user", "onCreate: $inputUsername , $password")
//            btnLogin.setOnClickListener {
//                val intent = Intent(this, ChooseService::class.java)
//                startActivity(intent)
//            }
//        }

    }
}