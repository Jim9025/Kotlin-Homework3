package com.example.lab8

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    // 使用 lateinit 延後初始化 RecyclerView 的 Adapter
    private lateinit var myAdapter: MyAdapter

    // 聯絡人資料清單
    private val contacts = mutableListOf<Contact>()

    // 處理 SecActivity 回傳結果
    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            val name = intent?.getStringExtra("name").orEmpty()
            val phone = intent?.getStringExtra("phone").orEmpty()

            // 新增聯絡人並更新列表
            contacts.add(Contact(name, phone))
            myAdapter.notifyItemInserted(contacts.lastIndex)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 取得畫面元件
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val btnAdd: Button = findViewById(R.id.btnAdd)

        // 設定 RecyclerView：垂直排列
        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        // 建立 Adapter 並連結 RecyclerView
        myAdapter = MyAdapter(contacts)
        recyclerView.adapter = myAdapter

        // 前往 SecActivity 以新增聯絡人
        btnAdd.setOnClickListener {
            val intent = Intent(this, SecActivity::class.java)
            startForResult.launch(intent)
        }
    }
}
