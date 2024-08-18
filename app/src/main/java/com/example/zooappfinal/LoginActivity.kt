package com.example.zooappfinal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginActivity : AppCompatActivity() {

    private lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        btnLogin = findViewById(R.id.btn_login)

        btnLogin.setOnClickListener {
            // Handle login button click
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}