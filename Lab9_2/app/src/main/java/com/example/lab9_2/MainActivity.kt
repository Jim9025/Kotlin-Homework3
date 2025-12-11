package com.example.lab9_2

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.pow

class MainActivity : AppCompatActivity() {

    companion object {
        private const val MAX_PROGRESS = 100
        private const val PROGRESS_DELAY_MS = 50L
    }

    // 畫面元件
    private lateinit var btnCalculate: Button
    private lateinit var edHeight: EditText
    private lateinit var edWeight: EditText
    private lateinit var edAge: EditText
    private lateinit var tvWeightResult: TextView
    private lateinit var tvFatResult: TextView
    private lateinit var tvBmiResult: TextView
    private lateinit var tvProgress: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var llProgress: LinearLayout
    private lateinit var rbBoy: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 綁定元件
        btnCalculate = findViewById(R.id.btnCalculate)
        edHeight = findViewById(R.id.edHeight)
        edWeight = findViewById(R.id.edWeight)
        edAge = findViewById(R.id.edAge)
        tvWeightResult = findViewById(R.id.tvWeightResult)
        tvFatResult = findViewById(R.id.tvFatResult)
        tvBmiResult = findViewById(R.id.tvBmiResult)
        tvProgress = findViewById(R.id.tvProgress)
        progressBar = findViewById(R.id.progressBar)
        llProgress = findViewById(R.id.llProgress)
        rbBoy = findViewById(R.id.btnBoy)

        btnCalculate.setOnClickListener {
            when {
                edHeight.text.isNullOrBlank() -> showToast("請輸入身高")
                edWeight.text.isNullOrBlank() -> showToast("請輸入體重")
                edAge.text.isNullOrBlank() -> showToast("請輸入年齡")
                else -> runThread()
            }
        }
    }

    // 顯示 Toast 訊息
    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    // 模擬檢測過程
    private fun runThread() {
        // 初始畫面文字
        tvWeightResult.text = "標準體重\n無"
        tvFatResult.text = "體脂肪\n無"
        tvBmiResult.text = "BMI\n無"

        // 初始化進度條
        progressBar.progress = 0
        tvProgress.text = "0%"
        llProgress.visibility = View.VISIBLE

        Thread {
            var progress = 0

            while (progress < MAX_PROGRESS) {
                try {
                    Thread.sleep(PROGRESS_DELAY_MS)
                } catch (_: InterruptedException) {
                }

                progress++

                runOnUiThread {
                    progressBar.progress = progress
                    tvProgress.text = "$progress%"
                }
            }

            // 計算結果
            val height = edHeight.text.toString().toDouble()
            val weight = edWeight.text.toString().toDouble()
            val age = edAge.text.toString().toDouble()
            val bmi = weight / ((height / 100).pow(2))

            val (standardWeight, bodyFat) = if (rbBoy.isChecked) {
                Pair((height - 80) * 0.7, 1.39 * bmi + 0.16 * age - 19.34)
            } else {
                Pair((height - 70) * 0.6, 1.39 * bmi + 0.16 * age - 9)
            }

            // 更新畫面
            runOnUiThread {
                llProgress.visibility = View.GONE
                tvWeightResult.text = "標準體重 \n${"%.2f".format(standardWeight)}"
                tvFatResult.text = "體脂肪 \n${"%.2f".format(bodyFat)}"
                tvBmiResult.text = "BMI \n${"%.2f".format(bmi)}"
            }
        }.start()
    }
}
