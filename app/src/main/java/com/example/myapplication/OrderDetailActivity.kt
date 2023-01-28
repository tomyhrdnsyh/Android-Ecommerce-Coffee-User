package com.example.myapplication

import android.content.Intent
import android.content.res.Resources
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderDetailActivity : AppCompatActivity() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerAdapterOrderDetailActivity.ViewHolder>? = null
    private val allOrders = ArrayList<GetOrderDetails>()

    companion object {
        const val EXTRA_ID = "1"
        const val EXTRA_STATUS = "Belum diterima"
        const val USERNAME = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationOnClickListener { super.onBackPressed() }

        val id = intent.getStringExtra(EXTRA_ID)
        val status = intent.getStringExtra(EXTRA_STATUS)

        getUserProfile()

        getOrderDetail(id)

        val btn_diterima = findViewById<Button>(R.id.btn_diterima)
        if (status == "Diterima") {
            btn_diterima.isEnabled = false
            btn_diterima.isClickable = false
            btn_diterima.setBackgroundResource(R.drawable.dangerroundedbutton)
        }
        else {
            btn_diterima.setOnClickListener{
                accProduct(id)
            }
        }

        val btn_buyagain = findViewById<Button>(R.id.btn_buyagain)
        btn_buyagain.setOnClickListener{
            startActivity(Intent(this@OrderDetailActivity, MainActivity::class.java))
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
                findViewById<TextView>(R.id.details_name).text = response.body()?.nama_lengkap.toString()
                findViewById<TextView>(R.id.details_phone).text = response.body()?.no_hp.toString()
                findViewById<TextView>(R.id.details_address).text = response.body()?.alamat.toString()
                findViewById<TextView>(R.id.details_address).text = response.body()?.alamat.toString()
            }

            override fun onFailure(call: Call<GetUserProfile>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun accProduct(id: String?) {
        RetrofitClient.instance.accProduct("$id").enqueue(object :
            Callback<MessageResponse> {
            override fun onResponse(
                call: Call<MessageResponse>,
                response: Response<MessageResponse>
            ) {
                val username = intent.getStringExtra(USERNAME)
                Toast.makeText(applicationContext, "${response.body()?.message}", Toast.LENGTH_LONG).show()
                val moveWithDataIntent = Intent(this@OrderDetailActivity, OrderActivity::class.java)
                moveWithDataIntent.putExtra(OrderActivity.USERNAME, username)
                startActivity(moveWithDataIntent)
            }

            override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun getOrderDetail(id: String?) {
        RetrofitClient.instance.getOrderDetails("$id").enqueue(object :
            Callback<ArrayList<GetOrderDetails>> {
            override fun onResponse(
                call: Call<ArrayList<GetOrderDetails>>,
                response: Response<ArrayList<GetOrderDetails>>
            ) {
                response.body()?.let {allOrders.addAll(it)}
//                Toast.makeText(applicationContext, "${response.body()}", Toast.LENGTH_LONG).show()

                val totalPrice = findViewById<TextView>(R.id.price)
                val price = allOrders.sumOf { it.product__price?.toInt()!! * it.orderdetails__qty?.toInt()!! }

                totalPrice.text = "Rp. "
                totalPrice.append(price.toString())

                if (allOrders.size > 0 ) {
                    val paymentMethod = findViewById<TextView>(R.id.payment_method)
                    if (response.body()?.get(0)!!.payment_type != null) {
                        paymentMethod.text = response.body()?.get(0)!!.payment_type
                    }

                    val delivery = findViewById<TextView>(R.id.ongkir)
                    delivery.text = "Rp. 10000"

                    val total = findViewById<TextView>(R.id.total)
                    total.text = "Rp. "
                    total.append((price+10000).toString())
                }

                layoutManager = LinearLayoutManager(this@OrderDetailActivity)

                val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
                recyclerView.layoutManager = layoutManager

                adapter = RecyclerAdapterOrderDetailActivity(allOrders)
                recyclerView.adapter = adapter
            }

            override fun onFailure(call: Call<ArrayList<GetOrderDetails>>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}