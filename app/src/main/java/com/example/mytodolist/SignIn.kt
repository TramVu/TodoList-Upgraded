package com.example.mytodolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth

class SignIn : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var emailText: TextView
    private lateinit var passwordText: TextView
    private lateinit var loginButton : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        auth = FirebaseAuth.auth
        emailText = findViewById(R.id.et_email)
        passwordText = findViewById(R.id.et_password)
        loginButton = findViewById(R.id.button_signIn)

        onStart()

        //get button listener
        loginButton.setOnClickListener{
            login(emailText.text.toString(),passwordText.text.toString())
        }

        val signUp = findViewById<TextView>(R.id.tv_signUp)
        signUp.setOnClickListener{
            val signUpPage = Intent (this, SignUp::class.java)
            startActivity(signUpPage)
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            Log.e("TAG",auth.currentUser?.email.toString())
            updateUI(currentUser)
        }
    }

    private fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email,password )
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "signInWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()

                }
            }

    }

    private fun updateUI(auth: FirebaseUser?) {
        val intent = Intent(this,MainActivity::class.java)
        intent.putExtra("Login",true)
        startActivity(intent)
    }

    }
}