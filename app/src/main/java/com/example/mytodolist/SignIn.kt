package com.example.mytodolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignIn : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var emailText: TextView
    private lateinit var passwordText: TextView
    private lateinit var loginArrow : TextView
    private lateinit var  signUp : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        auth = Firebase.auth
        emailText = findViewById(R.id.et_email)
        passwordText = findViewById(R.id.et_password)
        loginArrow = findViewById(R.id.button_signIn)
        signUp = findViewById(R.id.tv_signUp)

        onStart()

        //get button listener
        loginArrow.setOnClickListener{
            login(emailText.text.toString(),passwordText.text.toString())
        }

        //navigation
        signUp.setOnClickListener{
            val signUpPage = Intent (this, SignUp::class.java)
            Log.e(this.toString(), "succeed")
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
                    Toast.makeText(baseContext, "Log in successfully.",Toast.LENGTH_SHORT).show()
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",Toast.LENGTH_SHORT).show()
                }
            }

    }

    private fun updateUI(auth: FirebaseUser?) {
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }

}