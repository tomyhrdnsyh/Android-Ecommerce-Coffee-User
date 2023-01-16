package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationOnClickListener { super.onBackPressed() }

        val akun = findViewById<CardView>(R.id.akun)
        akun.setOnClickListener {
            startActivity(Intent(this@ProfileActivity, AccountActivity::class.java))
        }

        val pesanan = findViewById<CardView>(R.id.pesanan_saya)
        pesanan.setOnClickListener {
            startActivity(Intent(this@ProfileActivity, OrderActivity::class.java))
        }
    }
}