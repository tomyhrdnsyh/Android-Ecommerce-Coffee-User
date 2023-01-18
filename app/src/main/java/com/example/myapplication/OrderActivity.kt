package com.example.myapplication

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
    private var adapter: RecyclerView.Adapter<RecyclerAdapterOrder.ViewHolder>? = null
    private val allOrders = ArrayList<GetAllOrders>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationOnClickListener { super.onBackPressed() }

        getOrders()

        // onScrollVertically false agar item pada recyclerview tidak mendukung scroll
//        layoutManager = object : LinearLayoutManager(this) { override fun canScrollVertically() = false }
//
//        // recyclerview yang di file order activity
//        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
//        recyclerView.layoutManager = layoutManager
//
//        adapter = RecyclerAdapterOrder()
//        recyclerView.adapter = adapter

    }

    fun getOrders() {
        RetrofitClient.instance.getOrder(username = Auth().username.toString()).enqueue(object :
            Callback<ArrayList<GetAllOrders>> {
            override fun onResponse(
                call: Call<ArrayList<GetAllOrders>>,
                response: Response<ArrayList<GetAllOrders>>
            ) {
                response.body()?.let {allOrders.addAll(it)}
//                Toast.makeText(applicationContext, "${response.body()}", Toast.LENGTH_LONG).show()

                layoutManager = object : LinearLayoutManager(this@OrderActivity) { override fun canScrollVertically() = false }

                val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
                recyclerView.layoutManager = layoutManager

                adapter = RecyclerAdapterOrder(allOrders)
                recyclerView.adapter = adapter
            }

            override fun onFailure(call: Call<ArrayList<GetAllOrders>>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}