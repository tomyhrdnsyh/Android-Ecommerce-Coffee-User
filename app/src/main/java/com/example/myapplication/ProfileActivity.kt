package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView

class ProfileActivity : AppCompatActivity() {

    companion object {
        const val USERNAME = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val username = intent.getStringExtra(CartActivity.USERNAME)

        toolbar.setNavigationOnClickListener {
            val moveWithDataIntent = Intent(this, MainActivity::class.java)
            moveWithDataIntent.putExtra(AccountActivity.USERNAME, username)
            startActivity(moveWithDataIntent)
        }

        val akun = findViewById<CardView>(R.id.akun)
        akun.setOnClickListener {

            val moveWithDataIntent = Intent(this@ProfileActivity, AccountActivity::class.java)
            moveWithDataIntent.putExtra(AccountActivity.USERNAME, username)
            startActivity(moveWithDataIntent)
        }

        val pesanan = findViewById<CardView>(R.id.pesanan_saya)
        pesanan.setOnClickListener {

            val moveWithDataIntent = Intent(this@ProfileActivity, OrderActivity::class.java)
            moveWithDataIntent.putExtra(OrderActivity.USERNAME, username)
            startActivity(moveWithDataIntent)
        }
    }
}