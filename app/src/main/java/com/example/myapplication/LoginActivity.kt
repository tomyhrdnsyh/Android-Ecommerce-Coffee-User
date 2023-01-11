package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class LoginActivity : AppCompatActivity() {

    lateinit var no_hp: EditText
    lateinit var password: EditText
    lateinit var button_login: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        button_login = findViewById(R.id.btn_login)
        no_hp = findViewById(R.id.et_username)
        password = findViewById(R.id.et_password)

        // login checking
        button_login.setOnClickListener {
            if (no_hp.text.toString() == "" ||password.text.toString()  == "" ) {
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                Toast.makeText(applicationContext, "Login berhasil", Toast.LENGTH_SHORT).show()
            }
        }

        // when user click register
        val register = findViewById<TextView>(R.id.tv_register)
        register.setOnClickListener {
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }

    }

}