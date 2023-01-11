package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class OrderDetailActivity : AppCompatActivity() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerAdapterOrderdetails.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationOnClickListener { super.onBackPressed() }

        // onScrollVertically false agar item pada recyclerview tidak mendukung scroll
        layoutManager = object : LinearLayoutManager(this) { override fun canScrollVertically() = false }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = layoutManager

        adapter = RecyclerAdapterOrderdetails()
        recyclerView.adapter = adapter

        val btn_diterima = findViewById<Button>(R.id.btn_diterima)
        btn_diterima.setOnClickListener{
            Toast.makeText(applicationContext, "Produk diterima", Toast.LENGTH_SHORT).show()
        }

        val btn_buyagain = findViewById<Button>(R.id.btn_buyagain)
        btn_buyagain.setOnClickListener{
            startActivity(Intent(this@OrderDetailActivity, MainActivity::class.java))
        }
    }
}