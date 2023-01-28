package com.example.mytodolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var logged: Boolean = intent.getBooleanExtra("Login",false)
        if (logged) {
            val nextPage = Intent(this, Home::class.java)
            startActivity(nextPage)
        } else {
            val nextPage = Intent(this, SignIn::class.java)
            startActivity(nextPage)
        }

        finish()
    }
}