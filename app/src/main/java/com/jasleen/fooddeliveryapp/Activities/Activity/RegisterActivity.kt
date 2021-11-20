package com.jasleen.fooddeliveryapp.Activities.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.jasleen.fooddeliveryapp.Activities.HomeActivity
import com.jasleen.fooddeliveryapp.R

class RegisterActivity : AppCompatActivity() {
// add verification of email and phone number, phone number is currently not saved in database as it requires authentication first
// add user's name and address to the database


    lateinit var mAuth: FirebaseAuth
    lateinit var btn_register: Button
    lateinit var et_email_register: EditText
    lateinit var et_password_register: EditText
    lateinit var et_name_register: EditText
    lateinit var et_number_register: EditText
    lateinit var et_confirm_password_register: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mAuth = FirebaseAuth.getInstance()
        btn_register = findViewById(R.id.btn_register)
        et_email_register = findViewById(R.id.et_email_register)
        et_password_register = findViewById(R.id.et_password_register)
        et_name_register = findViewById(R.id.et_name_register)
        et_number_register = findViewById(R.id.et_number_register)
        et_confirm_password_register = findViewById(R.id.et_confirm_password_register)

        btn_register.setOnClickListener {
            val email = et_email_register.text.toString().trim()
            val password = et_password_register.text.toString().trim()
            val name = et_name_register.text.toString().trim()
            val number = et_number_register.text.toString().trim()
            val confirm_password = et_confirm_password_register.text.toString().trim()

            if(name.isEmpty()){
                et_name_register.error = "Name Required"
                et_name_register.requestFocus()
                return@setOnClickListener
            }

            if(email.isEmpty()){
                et_email_register.error = "Email Required"
                et_email_register.requestFocus()
                return@setOnClickListener
            }

            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                et_email_register.error = "Valid Email Required"
                et_email_register.requestFocus()
                return@setOnClickListener
            }

            if(number.isEmpty()){
                et_number_register.error = "Number Required"
                et_number_register.requestFocus()
                return@setOnClickListener
            }

            if(password.isEmpty() || password.length<6){
                et_password_register.error = "Minimum 6 Character Password Required"
                et_password_register.requestFocus()
                return@setOnClickListener
            }

            if(password != confirm_password){
                et_confirm_password_register.error = "Passwords Must Match"
                et_confirm_password_register.requestFocus()
                return@setOnClickListener
            }

            registerUser(email, password)

        }

    }

    private fun registerUser(email: String, password: String){
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){
                if(it.isSuccessful){
                    val intent = Intent(this@RegisterActivity, HomeActivity::class.java).apply{
                        val flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }
                    startActivity(intent)
                }else{
                    Toast.makeText(this@RegisterActivity, "Some Error Occurred", Toast.LENGTH_SHORT).show()
                }
            }
    }

}