package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderActivity : AppCompatActivity() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerAdapterOrderActivity.ViewHolder>? = null
    private val allOrders = ArrayList<GetAllOrders>()

    companion object {
        const val USERNAME = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val username = intent.getStringExtra(CartActivity.USERNAME)

        toolbar.setNavigationOnClickListener {
            val moveWithDataIntent = Intent(this, ProfileActivity::class.java)
            moveWithDataIntent.putExtra(AccountActivity.USERNAME, username)
            startActivity(moveWithDataIntent)
        }

        getOrders()

        // onScrollVertically false agar item pada recyclerview tidak mendukung scroll
//        layoutManager = object : LinearLayoutManager(this) { override fun canScrollVertically() = false }

    }

    private fun getOrders() {
        val username = intent.getStringExtra(CartActivity.USERNAME)
        RetrofitClient.instance.getOrder("$username").enqueue(object :
            Callback<ArrayList<GetAllOrders>> {
            override fun onResponse(
                call: Call<ArrayList<GetAllOrders>>,
                response: Response<ArrayList<GetAllOrders>>
            ) {
                response.body()?.let {allOrders.addAll(it)}
//                Toast.makeText(applicationContext, "${response.body()}", Toast.LENGTH_LONG).show()

                layoutManager = LinearLayoutManager(this@OrderActivity)

                val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
                recyclerView.layoutManager = layoutManager

                adapter = RecyclerAdapterOrderActivity(allOrders, username.toString())
                recyclerView.adapter = adapter
            }

            override fun onFailure(call: Call<ArrayList<GetAllOrders>>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}