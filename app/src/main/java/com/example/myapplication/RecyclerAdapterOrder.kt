package com.example.myapplication

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapterOrder(private val item: ArrayList<GetAllOrders>): RecyclerView.Adapter<RecyclerAdapterOrder.ViewHolder>() {

    private var qty = item.map { it.qty }
    private var title = item.map { it.product__name }
    private var price = item.map { it.order__gross_amount }
    private var status = item.map { it.order__status }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var itemQty: TextView
        var itemTitle: TextView
        var itemPrice: TextView
        var itemStatus: ImageView

        init {
            itemQty = itemView.findViewById(R.id.item_qty)
            itemTitle = itemView.findViewById(R.id.item_title)
            itemPrice = itemView.findViewById(R.id.item_price)
            itemStatus = itemView.findViewById(R.id.item_status)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.order_card, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemTitle.text = title[position]

        if (status[position] == "Belum diterima") {
            holder.itemStatus.setImageResource(R.drawable.icc_delivery);
        }
        else {
            holder.itemStatus.setImageResource(R.drawable.icc_check);
        }

        holder.itemQty.text = qty[position]
        holder.itemQty.append("x")

        holder.itemPrice.text = price[position]
    }

    override fun getItemCount(): Int {
        return item.size
    }
}