package com.example.lab8

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(
    private val data: MutableList<Contact>
) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    // ViewHolder：儲存每個項目的 View
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvName: TextView = itemView.findViewById(R.id.tvName)
        private val tvPhone: TextView = itemView.findViewById(R.id.tvPhone)
        private val imgDelete: ImageView = itemView.findViewById(R.id.imgDelete)

        fun bind(item: Contact, position: Int) {
            tvName.text = item.name
            tvPhone.text = item.phone

            // 刪除監聽器
            imgDelete.setOnClickListener {
                data.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, data.size)
            }
        }
    }

    // 建立 ViewHolder（載入 XML）
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_row, parent, false)
        return ViewHolder(view)
    }

    // 回傳資料筆數
    override fun getItemCount(): Int = data.size

    // 綁定資料給 ViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position], position)
    }
}
