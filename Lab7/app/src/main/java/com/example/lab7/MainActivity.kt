package com.example.lab7

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.GridView
import android.widget.ListView
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // --- 宣告元件 ---
        val spinner: Spinner = findViewById(R.id.spinner)
        val listView: ListView = findViewById(R.id.listView)
        val gridView: GridView = findViewById(R.id.gridView)

        // --- 準備水果資料 ---
        val typedArray = resources.obtainTypedArray(R.array.image_list)
        val priceRange = 10..100

        // 建立水果清單
        val items = List(typedArray.length()) { index ->
            val photo = typedArray.getResourceId(index, 0)
            val name = "水果${index + 1}"
            val price = priceRange.random()
            Item(photo, name, price)
        }
        typedArray.recycle()

        // Spinner 顯示「1個、2個、3個…」
        val counts = List(items.size) { index -> "${index + 1}個" }
        spinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            counts
        )

        // GridView 三欄顯示
        gridView.numColumns = 3
        gridView.adapter = MyAdapter(this, items, R.layout.adapter_vertical)

        // ListView 橫向顯示
        listView.adapter = MyAdapter(this, items, R.layout.adapter_horizontal)
    }
}
