package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapterOrderdetails: RecyclerView.Adapter<RecyclerAdapterOrderdetails.ViewHolder>() {

    private var title = arrayOf("Coffe Mmk", "Coffe Merah")
    private var qty = arrayOf("2", "1")
    private var price = arrayOf("21,000,000", "24,000,000")

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var itemQty: TextView
        var itemTitle: TextView
        var itemPrice: TextView

        init {
            itemQty = itemView.findViewById(R.id.item_qty)
            itemTitle = itemView.findViewById(R.id.item_title)
            itemPrice = itemView.findViewById(R.id.item_price)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.order_details_items, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemTitle.text = title[position]

        holder.itemQty.setText(qty[position])
        holder.itemQty.append("x")

        holder.itemPrice.setText("Rp. ")
        holder.itemPrice.append(price[position])
    }

    override fun getItemCount(): Int {
        return title.size
    }
}