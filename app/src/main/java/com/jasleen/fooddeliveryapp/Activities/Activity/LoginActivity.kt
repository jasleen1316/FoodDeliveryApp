package com.jasleen.fooddeliveryapp.Activities.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.jasleen.fooddeliveryapp.Activities.HomeActivity
import com.jasleen.fooddeliveryapp.R

class LoginActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    lateinit var btn_login: Button
    lateinit var et_email_login: EditText
    lateinit var et_password_login: EditText
    lateinit var txt_register: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()
        btn_login = findViewById(R.id.btn_login)
        et_email_login = findViewById(R.id.et_email_login)
        et_password_login = findViewById(R.id.et_password_login)
        txt_register = findViewById(R.id.txt_register)

        txt_register.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }

        btn_login.setOnClickListener {
            val email = et_email_login.text.toString().trim()
            val password = et_password_login.text.toString().trim()

            if(email.isEmpty()){
                et_email_login.error = "Email Required"
                et_email_login.requestFocus()
                return@setOnClickListener
            }

            if(password.isEmpty() || password.length<6){
                et_password_login.error = "Minimum 6 Charachter Password Required"
                et_password_login.requestFocus()
                return@setOnClickListener
            }

            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                et_email_login.error = "Valid Email Required"
                et_email_login.requestFocus()
                return@setOnClickListener
            }

            loginUser(email, password)


        }

        title = "Log In"
    }

    private fun loginUser(email: String, password: String){
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){
                if(it.isSuccessful){
                    val intent = Intent(this@LoginActivity, HomeActivity::class.java).apply{
                        val flags =Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }
                    startActivity(intent)
                }else{
                    Toast.makeText(this@LoginActivity, "Some Error Occurred", Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onStart() {
        super.onStart()

        mAuth.currentUser?.let {
            val intent = Intent(this@LoginActivity, HomeActivity::class.java).apply{
                val flags =Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
        }
    }

}