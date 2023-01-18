package com.example.myapplication

import android.app.Dialog
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


class RecyclerAdapterHomepage(private val item: ArrayList<GetAllProduct>): RecyclerView.Adapter<RecyclerAdapterHomepage.ViewHolder>() {

    private var product_id = item.map { it.product_id }
    private var title = item.map { it.name }
    private var type = item.map { it.product_type__name }
    private var stock = item.map { it.stock }
    private var price = item.map { it.price }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_home, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemTitle.text = title[position]
        holder.itemType.text = type[position]


        holder.itemStock.setText("Qty : ")
        holder.itemStock.append(stock[position])

        holder.itemPrice.text = price[position]
    }

    override fun getItemCount(): Int {
        return item.size
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

                setValueInDialog(dialogBinding, title[position], price[position], type[position])   //  set value ke popup
                qty_increase_reduce(dialogBinding, myDialog)    // function untuk naik turunkan kuantitas

                val checkout = myDialog.findViewById<Button>(R.id.btn_checkout)
                checkout.setOnClickListener{
                    val product_id = product_id[position].toString()
                    val qty = dialogBinding.findViewById<TextView>(R.id.qty).text

                    addOrders(product_id, "admin-aja", qty.toString(), itemView) // add order when user click checkout button
                    myDialog.dismiss()
                }
            }

            addToCart.setOnClickListener{
                val position: Int = adapterPosition

                Toast.makeText(itemView.context, "${title[position]} succesfull add to cart", Toast.LENGTH_LONG).show()
            }
        }

        fun setValueInDialog(view: View, title: String?, price: String?, type: String?) {
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

    private fun addOrders(product_id: String, username: String, quantitas: String, view: View) {
        RetrofitClient.instance.addOrder(
            product_id, username, quantitas
        ).enqueue(object : Callback<PostOrders> {
            override fun onResponse(call: Call<PostOrders>, response: Response<PostOrders>) {

                val checkoutDone = LayoutInflater.from(view.context).inflate(R.layout.dialog_checkout, null)
                val myDialog = Dialog(view.context)
                myDialog.setContentView(checkoutDone)

                myDialog.setCancelable(true)

                myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                myDialog.show()

            }

            override fun onFailure(call: Call<PostOrders>, t: Throwable) {
                // t.message menampilkan pesan error dari system
                Toast.makeText(view.context, "Gagal koneksi ke server", Toast.LENGTH_LONG).show()
            }

        })
    }

}
