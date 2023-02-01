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
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignUp : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var emailText : TextView
    private lateinit var passwordText : TextView
    private lateinit var signupButton : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth = Firebase.auth
        emailText = findViewById(R.id.et_email_signUp)
        passwordText = findViewById(R.id.et_password_signUp)
        signupButton = findViewById(R.id.button_signUp)

        //navigation
        val signIntext = findViewById<TextView>(R.id.tv_signIn1)
        signIntext.setOnClickListener{
            val signInpage = Intent (this, SignIn::class.java)
            startActivity(signInpage)
            finish()
        }

        signupButton.setOnClickListener{
            if (emailText.text.toString().isNotEmpty() && passwordText.text.toString().isNotEmpty()){
                createAccount(emailText.text.toString(), passwordText.text.toString())
            }else {
                Toast.makeText(this, "Empty fields are not allowed !", Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun createAccount(email: String, password: String) {
        // [START create_user_with_email]
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "createUserWithEmail:success")
                    Toast.makeText(baseContext, "Create account successfully",
                        Toast.LENGTH_SHORT).show()
                    val user = auth.currentUser
                    updateUI()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
        // [END create_user_with_email]
    }

    private fun updateUI() {
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }
}