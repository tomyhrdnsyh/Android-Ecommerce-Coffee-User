package com.example.myapplication

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartActivity : AppCompatActivity() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private lateinit var adapter: RecyclerAdapterCartActivity
    private val allProduct = ArrayList<GetAllCart>()

    companion object {
        const val USERNAME = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationOnClickListener { super.onBackPressed() }

        getUserProfile()

        getAllCart()

        val username = intent.getStringExtra(USERNAME)

        val editProfile = findViewById<TextView>(R.id.edit_details)
        editProfile.setOnClickListener{
            val moveWithDataIntent = Intent(this, AccountActivity::class.java)
            moveWithDataIntent.putExtra(AccountActivity.FROM_CART, "1")
            moveWithDataIntent.putExtra(AccountActivity.USERNAME, username)
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

    private fun checkoutFromCart() {
        val username = intent.getStringExtra(USERNAME)
        RetrofitClient.instance.addOrderFromCart("$username").enqueue(object :
            Callback<MessageResponse> {
            override fun onResponse(call: Call<MessageResponse>, response: Response<MessageResponse>) {

                val url = response.body()?.message

                val moveWithDataIntent = Intent(this@CartActivity, PaymentActivity::class.java)
                moveWithDataIntent.putExtra(PaymentActivity.URL, url)
                startActivity(moveWithDataIntent)
            }

            override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                Toast.makeText(this@CartActivity, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    fun getAllCart() {
        val username = intent.getStringExtra(USERNAME)
        RetrofitClient.instance.getCart("$username").enqueue(object :
            Callback<ArrayList<GetAllCart>> {
            override fun onResponse(
                call: Call<ArrayList<GetAllCart>>,
                response: Response<ArrayList<GetAllCart>>
            ) {
                response.body()?.let {allProduct.addAll(it)}

                val btn_checkout = findViewById<Button>(R.id.btn_checkout)
                if (allProduct.size < 1) {
                    btn_checkout.isEnabled = false
                    btn_checkout.isClickable = false
                    btn_checkout.setBackgroundResource(R.drawable.dangerroundedbutton)
                }
                else {
                    btn_checkout.setOnClickListener{
                        checkoutFromCart()

//                        val dialogBinding = layoutInflater.inflate(R.layout.dialog_checkout, null)
//                        val myDialog = Dialog(this@CartActivity)
//                        myDialog.setContentView(dialogBinding)

//                        myDialog.setCancelable(true)
//                        myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//                        myDialog.show()
//                        val username = intent.getStringExtra(USERNAME)
//                        val btn_detail = dialogBinding.findViewById<Button>(R.id.btn_detail)
//
//                        btn_detail.setOnClickListener{
//                            val moveWithDataIntent = Intent(this@CartActivity, OrderActivity::class.java)
//                            moveWithDataIntent.putExtra(OrderActivity.USERNAME, username)
//                            startActivity(moveWithDataIntent)
//
//                        }
                    }
                }

                val totalPrice = findViewById<TextView>(R.id.price)
                val price = allProduct.sumOf { it.product__price?.toInt()!! * it.qty?.toInt()!! }

                totalPrice.text = "Rp. "
                totalPrice.append(price.toString())

                if (allProduct.size > 0 ) {
                    val delivery = findViewById<TextView>(R.id.ongkir)
                    delivery.text = "Rp. 10000"

                    val total = findViewById<TextView>(R.id.total)
                    total.text = "Rp. "
                    total.append((price + 10000).toString())
                }

                layoutManager = object : LinearLayoutManager(this@CartActivity) { override fun canScrollVertically() = false }

                val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
                recyclerView.layoutManager = layoutManager

                adapter = RecyclerAdapterCartActivity(allProduct)
                recyclerView.adapter = adapter
            }

            override fun onFailure(call: Call<ArrayList<GetAllCart>>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    fun deleteCart(item: ArrayList<GetAllCart>, itemView: View, position: Int) {

        RetrofitClient.instance.deleteCart("${item[position].cart_id}").enqueue(object :
            Callback<MessageResponse> {
            override fun onResponse(call: Call<MessageResponse>, response: Response<MessageResponse>) {
                Toast.makeText(itemView.context, "${response.body()?.message}, Page must reload", Toast.LENGTH_SHORT).show()
//                startActivity(Intent(this@CartActivity, MainActivity::class.java))
            }

            override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                Toast.makeText(itemView.context, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}

