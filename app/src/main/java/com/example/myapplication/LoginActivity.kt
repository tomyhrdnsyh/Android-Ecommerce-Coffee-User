package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    lateinit var username: EditText
    lateinit var password: EditText
    lateinit var button_login: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        button_login = findViewById(R.id.btn_login)
        username = findViewById(R.id.et_username)
        password = findViewById(R.id.et_password)

        // login checking
        button_login.setOnClickListener {
            if (username.text.toString() == "" ||password.text.toString()  == "" ) {
                Toast.makeText(applicationContext, "Username password tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
            else
            {
                authLogin(username.text.toString(), password.text.toString())
            }
        }

        // when user click register
        val register = findViewById<TextView>(R.id.tv_register)
        register.setOnClickListener {
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }

    }
    private fun authLogin(username: String, password: String) {
        RetrofitClient.instance.auth(
            username,
            password
        ).enqueue(object : Callback<MessageResponse> {

            override fun onResponse(call: Call<MessageResponse>, response: Response<MessageResponse>) {
                val msg = response.body()?.message.toString()
                if (msg == "200") {
                    val moveWithDataIntent = Intent(this@LoginActivity, MainActivity::class.java)
                    moveWithDataIntent.putExtra(MainActivity.USERNAME, username)
                    startActivity(moveWithDataIntent)
                    Toast.makeText(applicationContext, "Login berhasil", Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(this@LoginActivity, msg, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                // t.message menampilkan pesan error dari system
                Toast.makeText(applicationContext, "Gagal koneksi ke server", Toast.LENGTH_LONG).show()
            }
        })
    }

}