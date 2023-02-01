package com.example.mytodolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var  mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = Firebase.auth

        var islogged: Boolean = mAuth.currentUser != null
        if (islogged) {
            val nextPage = Intent(this, Home::class.java)
            startActivity(nextPage)
        } else {
            val nextPage = Intent(this, SignIn::class.java)
            startActivity(nextPage)
        }
        finish ()


    }
}