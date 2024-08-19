package com.example.zooappfinal

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var signInButton: Button
    private lateinit var signUpTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Initialize views using findViewById
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        signInButton = findViewById(R.id.signInButton)

        // Quick login method for development
        signInButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

//        signInButton.setOnClickListener {
//            val email = emailEditText.text.toString().trim()
//            val password = passwordEditText.text.toString().trim()
//
//            if (validateInput(email, password)) {
//                signInUser(email, password)
//            }
//        }
    }

//    private fun validateInput(email: String, password: String): Boolean {
//        if (email.isEmpty()) {
//            emailEditText.error = "Email is required"
//            emailEditText.requestFocus()
//            return false
//        }
//
//        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            emailEditText.error = "Please provide a valid email"
//            emailEditText.requestFocus()
//            return false
//        }
//
//        if (password.isEmpty()) {
//            passwordEditText.error = "Password is required"
//            passwordEditText.requestFocus()
//            return false
//        }
//
//        if (password.length < 6) {
//            passwordEditText.error = "Password must be at least 6 characters"
//            passwordEditText.requestFocus()
//            return false
//        }
//        Toast.makeText(this, "Validated", Toast.LENGTH_LONG).show()
//        return true
//    }
//
//    private fun signInUser(email: String, password: String) {
//        auth.signInWithEmailAndPassword(email, password)
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    // Sign in success, navigate to the main activity
//                    val intent = Intent(this, MainActivity::class.java)
//                    Toast.makeText(this, "Login successful", Toast.LENGTH_LONG).show()
//                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                    startActivity(intent)
//                } else {
//                    // If sign in fails, display a message to the user.
//                    Toast.makeText(this, "Authentication failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
//                }
//            }
//    }
}
