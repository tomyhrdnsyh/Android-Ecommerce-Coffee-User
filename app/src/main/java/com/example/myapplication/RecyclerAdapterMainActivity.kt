package com.example.myapplication

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RecyclerAdapterMainActivity(var item: ArrayList<GetAllProduct>,
                                  var username: String): RecyclerView.Adapter<RecyclerAdapterMainActivity.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_home, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (item[position].product_category__name == "Minuman") {
            holder.itemImage.setImageResource(R.drawable.coffee)
        }
        else {
            holder.itemImage.setImageResource(R.drawable.ic_snack)
        }

        holder.itemTitle.text = item[position].name
        holder.itemType.text = item[position].product_type__name


        holder.itemStock.text = "Qty : "
        holder.itemStock.append(item[position].stock)

        holder.itemPrice.text = item[position].price
    }

    override fun getItemCount(): Int {
        return item.size
    }

    fun setFilteredList(item: ArrayList<GetAllProduct>) {
        this.item = item
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var itemImage: ImageView
        var itemTitle: TextView
        var itemType: TextView
        var itemStock: TextView
        var itemPrice: TextView
        var addToCart: ImageView
        var quantity: Int = 1

        init {
            itemImage = itemView.findViewById(R.id.item_image)
            itemTitle = itemView.findViewById(R.id.item_title)
            itemType = itemView.findViewById(R.id.item_type)
            itemStock = itemView.findViewById(R.id.item_stock)
            itemPrice = itemView.findViewById(R.id.item_price)
            addToCart = itemView.findViewById(R.id.add_to_cart)



            itemView.setOnClickListener{
                val position: Int = adapterPosition

                val dialogBinding = LayoutInflater.from(itemView.context).inflate(R.layout.popup_select_qty, null)
                val myDialog = Dialog(itemView.context, R.style.mydialog)
                myDialog.setContentView(dialogBinding)

                myDialog.setCancelable(true)

                myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                myDialog.show()

                setValueInDialog(dialogBinding, item[position].name, item[position].price, item[position].product_type__name)   //  set value ke popup
                qty_increase_reduce(dialogBinding, myDialog)    // function untuk naik turunkan kuantitas

                val checkout = myDialog.findViewById<Button>(R.id.btn_checkout)
                checkout.setOnClickListener{
                    val product_id = item[position].product_id.toString()
                    val qty = dialogBinding.findViewById<TextView>(R.id.qty).text

                    addOrders(product_id, username, qty.toString(), itemView) // add order when user click checkout button
                    myDialog.dismiss()
                }

                val addCart = myDialog.findViewById<Button>(R.id.btn_add_to_cart)
                addCart.setOnClickListener{
                    val productId = item[position].product_id.toString()
                    val qty = dialogBinding.findViewById<TextView>(R.id.qty).text.toString()

                    addCart(productId, username, qty, itemView)
//                    itemView.context.startActivity(Intent(itemView.context, MainActivity::class.java))
                    myDialog.dismiss()

                }
            }

            addToCart.setOnClickListener{
                val position: Int = adapterPosition
                addCart(item[position].product_id.toString(), username, "$quantity", itemView)
            }
        }

        fun setValueInDialog(view: View, title: String?, price: String?, type: String?) {

            if (item[position].product_category__name == "Minuman") {
                view.findViewById<ImageView>(R.id.image).setImageResource(R.drawable.coffee)
            }
            else {
                view.findViewById<ImageView>(R.id.image).setImageResource(R.drawable.ic_snack)
            }
            view.findViewById<TextView>(R.id.title).text = "$type "
            view.findViewById<TextView>(R.id.title).append(title)
            view.findViewById<TextView>(R.id.price).text = price
        }

        fun updateValue(view: View){
            view.findViewById<TextView>(R.id.qty).text = quantity.toString()
        }

        fun qty_increase_reduce(view: View, dialog: Dialog) {
            view.findViewById<ImageView>(R.id.increase_qty).setOnClickListener{
                quantity++
                updateValue(view)
            }
            view.findViewById<ImageView>(R.id.reduce_qty).setOnClickListener{
                if (quantity > 1) { quantity-- }
                else { dialog.dismiss() }
                updateValue(view)
            }
        }

    }

    private fun addCart(product_id: String, username: String, quantity: String, itemView: View) {
        RetrofitClient.instance.addCart(
            product_id, username, quantity
        ).enqueue(object : Callback<MessageResponse> {
            override fun onResponse(
                call: Call<MessageResponse>,
                response: Response<MessageResponse>
            ) {
                Toast.makeText(itemView.context, "${response.body()?.message}", Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                // t.message menampilkan pesan error dari system
                Toast.makeText(itemView.context, "Gagal koneksi ke server", Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun addOrders(product_id: String, username: String, quantity: String, view: View) {
        RetrofitClient.instance.addOrder(
            product_id, username, quantity
        ).enqueue(object : Callback<PostOrders> {
            override fun onResponse(call: Call<PostOrders>, response: Response<PostOrders>) {

                val checkoutDone = LayoutInflater.from(view.context).inflate(R.layout.dialog_checkout, null)
                val myDialog = Dialog(view.context)
                myDialog.setContentView(checkoutDone)

                myDialog.setCancelable(true)

                myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                myDialog.show()

                val btn_detail = checkoutDone.findViewById<Button>(R.id.btn_detail)
                btn_detail.setOnClickListener{
                    val moveWithDataIntent = Intent(view.context, OrderActivity::class.java)
                    moveWithDataIntent.putExtra(OrderActivity.USERNAME, username)
                    view.context.startActivity(moveWithDataIntent)
                }


                // when user click btn_detail

            }

            override fun onFailure(call: Call<PostOrders>, t: Throwable) {
                // t.message menampilkan pesan error dari system
                Toast.makeText(view.context, "Gagal koneksi ke server", Toast.LENGTH_LONG).show()
            }

        })
    }

}
