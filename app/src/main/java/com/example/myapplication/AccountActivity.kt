package com.example.myapplication

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccountActivity : AppCompatActivity() {

    companion object {
        const val FROM_CART = "0"
        const val USERNAME = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationOnClickListener { super.onBackPressed() }

        val btn_save = findViewById<Button>(R.id.btn_save)

        val nama_lengkap = findViewById<EditText>(R.id.et_nama).text
        val no_hp = findViewById<EditText>(R.id.et_nohp).text
        val email = findViewById<EditText>(R.id.et_email).text
        val password = findViewById<TextInputEditText>(R.id.et_password).text
        val alamat = findViewById<EditText>(R.id.et_alamat).text

        btn_save.setOnClickListener{
            editUserProfile(nama_lengkap.toString(), no_hp.toString(), email.toString(), password.toString(), alamat.toString())
        }

        getUserProfile()

        val btn_logout = findViewById<Button>(R.id.btn_logout)
        btn_logout.setOnClickListener {
            val dialogBinding = layoutInflater.inflate(R.layout.dialog_logout, null)
            val myDialog = Dialog(this)
            myDialog.setContentView(dialogBinding)

            myDialog.setCancelable(true)
            myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            myDialog.show()

            val btn_acc_logout = dialogBinding.findViewById<Button>(R.id.btn_accept_logout)
            btn_acc_logout.setOnClickListener{
                startActivity(Intent(this@AccountActivity, LoginActivity::class.java))
            }

            val btn_cancel_logout = dialogBinding.findViewById<Button>(R.id.btn_cancel_logout)
            btn_cancel_logout.setOnClickListener{
                startActivity(Intent(this@AccountActivity, MainActivity::class.java))
            }
        }
    }

    private fun editUserProfile(nama_lengkap: String, no_hp:String, email: String, password: String, alamat: String) {
        val username = intent.getStringExtra(CartActivity.USERNAME)

        RetrofitClient.instance.editUserProfile(
            username = username.toString(),
            nama_lengkap = nama_lengkap,
            no_hp=no_hp,
            email=email,
            password=password,
            alamat=alamat).enqueue(object :
            Callback<MessageResponse> {
            override fun onResponse(
                call: Call<MessageResponse>,
                response: Response<MessageResponse>
            ) {
                val fromCart = intent.getStringExtra(FROM_CART)
                Toast.makeText(applicationContext, "${response.body()?.message}", Toast.LENGTH_LONG).show()
                if (fromCart == "1") {
                    val moveWithDataIntent = Intent(this@AccountActivity, CartActivity::class.java)
                    moveWithDataIntent.putExtra(CartActivity.USERNAME, username)
                    startActivity(moveWithDataIntent)
                }
                else {
                    val moveWithDataIntent = Intent(this@AccountActivity, ProfileActivity::class.java)
                    moveWithDataIntent.putExtra(ProfileActivity.USERNAME, username)
                    startActivity(moveWithDataIntent)
                }
            }

            override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun getUserProfile() {
        val username = intent.getStringExtra(CartActivity.USERNAME)

        RetrofitClient.instance.getUserProfile("$username").enqueue(object :
            Callback<GetUserProfile> {
            override fun onResponse(
                call: Call<GetUserProfile>,
                response: Response<GetUserProfile>
            ) {
                findViewById<EditText>(R.id.et_nama).hint = response.body()?.nama_lengkap
                findViewById<EditText>(R.id.et_nohp).hint = response.body()?.no_hp
                findViewById<EditText>(R.id.et_email).hint = response.body()?.email
                findViewById<EditText>(R.id.et_alamat).hint = response.body()?.alamat
            }

            override fun onFailure(call: Call<GetUserProfile>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}