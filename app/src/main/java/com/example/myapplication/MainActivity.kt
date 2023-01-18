package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerAdapterHomepage.ViewHolder>? = null
    private val allProduct = ArrayList<GetAllProduct>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getAllProduct()

        val cart = findViewById<ImageView>(R.id.cart)
        cart.setOnClickListener {
            startActivity(Intent(this@MainActivity, CartActivity::class.java))
        }

        val profile = findViewById<ImageView>(R.id.profile)
        profile.setOnClickListener {
            startActivity(Intent(this@MainActivity, ProfileActivity::class.java))
        }
    }


    fun getAllProduct() {
        RetrofitClient.instance.getAllProduct().enqueue(object :
            Callback<ArrayList<GetAllProduct>> {
            override fun onResponse(
                call: Call<ArrayList<GetAllProduct>>,
                response: Response<ArrayList<GetAllProduct>>
            ) {
                response.body()?.let {allProduct.addAll(it)}
//                Toast.makeText(applicationContext, "${response.body()}", Toast.LENGTH_LONG).show()

                layoutManager = LinearLayoutManager(this@MainActivity)

                val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
                recyclerView.layoutManager = layoutManager

                adapter = RecyclerAdapterHomepage(allProduct)
                recyclerView.adapter = adapter
            }

            override fun onFailure(call: Call<ArrayList<GetAllProduct>>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}