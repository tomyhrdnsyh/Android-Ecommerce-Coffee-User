package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val login = findViewById<TextView>(R.id.tv_login)
        login.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }

        val username = findViewById<EditText>(R.id.et_username_register).text
        val fullname = findViewById<EditText>(R.id.et_fullname).text
        val no_hp = findViewById<EditText>(R.id.et_nohp).text
        val email = findViewById<EditText>(R.id.et_email).text
        val password = findViewById<EditText>(R.id.et_password).text
        val alamat = findViewById<EditText>(R.id.et_alamat).text

        val register = findViewById<Button>(R.id.btn_register)
        register.setOnClickListener{
            registration(username.toString(), fullname.toString(), no_hp.toString(),
                email.toString(), password.toString(), alamat.toString())
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun registration(username: String, fullname: String, no_hp: String, email: String,
                             password: String, alamat: String) {
        RetrofitClient.instance.register(
            username = username,
            full_name = fullname,
            no_hp = no_hp,
            email = email,
            password = password,
            address = alamat
        ).enqueue(object :
            Callback<MessageResponse> {
            override fun onResponse(call: Call<MessageResponse>, response: Response<MessageResponse>) {
                Toast.makeText(this@RegisterActivity, "${response.body()?.message}", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                Toast.makeText(this@RegisterActivity, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}