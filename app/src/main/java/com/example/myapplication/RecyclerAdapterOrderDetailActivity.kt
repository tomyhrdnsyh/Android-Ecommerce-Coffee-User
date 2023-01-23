package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapterOrderDetailActivity(private val item: ArrayList<GetOrderDetails>): RecyclerView.Adapter<RecyclerAdapterOrderDetailActivity.ViewHolder>() {

    private var title = item.map { it.product__name }
    private var type = item.map { it.product__product_type__name }
    private var qty = item.map { it.orderdetails__qty }
    private var price = item.map { it.product__price }

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
        holder.itemTitle.text = type[position]
        holder.itemTitle.append(" "+title[position])

        holder.itemQty.text = qty[position]
        holder.itemQty.append("x")

        holder.itemPrice.text = "Rp. "
        holder.itemPrice.append(price[position])
    }

    override fun getItemCount(): Int {
        return title.size
    }
}