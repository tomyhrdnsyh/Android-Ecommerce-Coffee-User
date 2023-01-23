package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapterCartActivity(var item: ArrayList<GetAllCart>): RecyclerView.Adapter<RecyclerAdapterCartActivity.ViewHolder>() {

    fun setFilteredList(item: ArrayList<GetAllCart>) {
        this.item = item
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.checkout_detail_card, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (item[position].product__product_category__name == "Minuman") {
            holder.itemImage.setImageResource(R.drawable.coffee)
        }
        else {
            holder.itemImage.setImageResource(R.drawable.ic_snack)
        }

        holder.itemTitle.text = item[position].product__name
        holder.itemType.text = item[position].product__product_type__name

        holder.itemPrice.text = "Rp. "
        holder.itemPrice.append(item[position].product__price)

        holder.itemQty.text = "Qty : "
        holder.itemQty.append(item[position].qty)
    }

    override fun getItemCount(): Int {
        return item.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var itemImage: ImageView
        var itemTitle: TextView
        var itemType: TextView
        var itemQty: TextView
        var itemPrice: TextView
        var deleteCo: ImageView

        init {
            itemImage = itemView.findViewById(R.id.item_image_co)
            itemTitle = itemView.findViewById(R.id.item_title_co)
            itemType = itemView.findViewById(R.id.item_type_co)
            itemPrice = itemView.findViewById(R.id.item_price_co)
            itemQty = itemView.findViewById(R.id.item_qty)
            deleteCo = itemView.findViewById(R.id.delete_co)

            deleteCo.setOnClickListener{
                val position: Int = adapterPosition
                CartActivity().deleteCart(item = item, itemView = itemView, position =  position)
            }
        }

    }
}