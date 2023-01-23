package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList
import androidx.appcompat.widget.SearchView
import androidx.cardview.widget.CardView

class MainActivity : AppCompatActivity() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private lateinit var adapter: RecyclerAdapterMainActivity
    private val allProduct = ArrayList<GetAllProduct>()

    companion object {
        const val USERNAME = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getAllProduct()
        getUserProfile()

        var username = intent.getStringExtra(USERNAME)

        val cart = findViewById<ImageView>(R.id.cart)
        cart.setOnClickListener {
            val moveWithDataIntent = Intent(this@MainActivity, CartActivity::class.java)
            moveWithDataIntent.putExtra(CartActivity.USERNAME, username)
            startActivity(moveWithDataIntent)
        }

        val profile = findViewById<ImageView>(R.id.profile)
        profile.setOnClickListener {
            val moveWithDataIntent = Intent(this@MainActivity, ProfileActivity::class.java)
            moveWithDataIntent.putExtra(ProfileActivity.USERNAME, username)
            startActivity(moveWithDataIntent)
        }
    }

    private fun getUserProfile() {
        val username = intent.getStringExtra(USERNAME)
        RetrofitClient.instance.getUserProfile("$username").enqueue(object :
            Callback<GetUserProfile> {
            override fun onResponse(
                call: Call<GetUserProfile>,
                response: Response<GetUserProfile>
            ) {
                findViewById<TextView>(R.id.user_location).text = response.body()?.alamat.toString()
            }

            override fun onFailure(call: Call<GetUserProfile>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getAllProduct() {
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

                val searchView = findViewById<SearchView>(R.id.searchView)

                var username = intent.getStringExtra(USERNAME)

                adapter = RecyclerAdapterMainActivity(allProduct, username = username.toString())
                recyclerView.adapter = adapter

                // filter minuman
                val minuman = findViewById<CardView>(R.id.minuman)
                minuman.setOnClickListener {
                    val newItem = ArrayList<GetAllProduct>()
                    for (item in response.body()!!) {
                        if (item.product_category__name == "Minuman") {
                            newItem.add(item)
                        }
                    }
                    adapter.setFilteredList(newItem)
                }

                val snack = findViewById<CardView>(R.id.cemilan)
                snack.setOnClickListener {
                    val newItem = ArrayList<GetAllProduct>()
                    for (item in response.body()!!) {
                        if (item.product_category__name == "Cemilan") {
                            newItem.add(item)
                        }
                    }
                    adapter.setFilteredList(newItem)
                }

                val allItem = findViewById<CardView>(R.id.all_item)
                allItem.setOnClickListener {
                    val newItem = ArrayList<GetAllProduct>()
                    response.body()?.let {newItem.addAll(it)}
                    adapter.setFilteredList(newItem)
                }

                // filter search
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(p0: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(p0: String?): Boolean {
                        filterList(p0, adapter, allProduct)
                        return true
                    }
                })
            }

            override fun onFailure(call: Call<ArrayList<GetAllProduct>>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun filterList(query: String?, adapter: RecyclerAdapterMainActivity, allProduct: ArrayList<GetAllProduct>) {
        if (query != null) {
            val filteredList = ArrayList<GetAllProduct>()
            for (i in allProduct) {
                if (i.name?.lowercase(Locale.ROOT)!!.contains(query))
                    filteredList.add(i)
            }
            if (filteredList.isEmpty()){
                Toast.makeText(this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show()
            }
            else {
                adapter.setFilteredList(filteredList)
            }
        }
    }
}