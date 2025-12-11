package com.example.lab7

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes

class MyAdapter(
    context: Context,
    private val items: List<Item>,
    @LayoutRes private val layoutResId: Int
) : ArrayAdapter<Item>(context, layoutResId, items) {

    private val inflater = LayoutInflater.from(context)

    private data class ViewHolder(
        val imgPhoto: ImageView,
        val tvMsg: TextView
    )

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // 建立或重用 item view + viewHolder
        val (view, holder) = if (convertView == null) {
            val v = inflater.inflate(layoutResId, parent, false)
            val h = ViewHolder(
                v.findViewById(R.id.imgPhoto),
                v.findViewById(R.id.tvMsg)
            )
            v.tag = h
            v to h
        } else {
            convertView to (convertView.tag as ViewHolder)
        }

        val item = items[position]

        // 設定圖片
        holder.imgPhoto.setImageResource(item.photo)

        // 垂直版只顯示名稱，其他版顯示名稱＋價格
        holder.tvMsg.text = if (layoutResId == R.layout.adapter_vertical) {
            item.name
        } else {
            "${item.name}: ${item.price}元"
        }

        return view
    }
}
