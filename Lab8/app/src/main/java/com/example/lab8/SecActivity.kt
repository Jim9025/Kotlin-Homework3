package com.example.lab8

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SecActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sec)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 取得畫面元件
        val edName: EditText = findViewById(R.id.edName)
        val edPhone: EditText = findViewById(R.id.edPhone)
        val btnSend: Button = findViewById(R.id.btnSend)

        // 送出按鈕：檢查輸入並回傳結果
        btnSend.setOnClickListener {
            val name = edName.text.toString()
            val phone = edPhone.text.toString()

            when {
                name.isEmpty() -> showToast("請輸入姓名")
                phone.isEmpty() -> showToast("請輸入電話")
                else -> {
                    val resultIntent = Intent().apply {
                        putExtra("name", name)
                        putExtra("phone", phone)
                    }
                    setResult(Activity.RESULT_OK, resultIntent)
                    finish()
                }
            }
        }
    }

    // 顯示 Toast 訊息
    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
