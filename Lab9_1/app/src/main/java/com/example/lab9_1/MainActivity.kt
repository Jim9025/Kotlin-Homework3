package com.example.lab9_1

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.Button
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    companion object {
        private const val MSG_RABBIT = 1
        private const val MSG_TURTLE = 2
        private const val GOAL = 100
    }

    // 兔子與烏龜的進度
    @Volatile
    private var progressRabbit = 0

    @Volatile
    private var progressTurtle = 0

    // 元件
    private lateinit var btnStart: Button
    private lateinit var sbRabbit: SeekBar
    private lateinit var sbTurtle: SeekBar

    // 接收執行緒傳來的賽況
    private val handler = Handler(Looper.getMainLooper()) { msg: Message ->
        when (msg.what) {
            MSG_RABBIT -> {
                sbRabbit.progress = progressRabbit
                if (progressRabbit >= GOAL && progressTurtle < GOAL) {
                    showToast("兔子勝利")
                    btnStart.isEnabled = true
                }
            }
            MSG_TURTLE -> {
                sbTurtle.progress = progressTurtle
                if (progressTurtle >= GOAL && progressRabbit < GOAL) {
                    showToast("烏龜勝利")
                    btnStart.isEnabled = true
                }
            }
        }
        true
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

        // 綁定元件
        btnStart = findViewById(R.id.btnStart)
        sbRabbit = findViewById(R.id.sbRabbit)
        sbTurtle = findViewById(R.id.sbTurtle)

        btnStart.setOnClickListener {
            resetRace()
            runRabbit()
            runTurtle()
        }
    }

    private fun resetRace() {
        btnStart.isEnabled = false
        progressRabbit = 0
        progressTurtle = 0
        sbRabbit.progress = 0
        sbTurtle.progress = 0
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    // 用 Thread 模擬兔子移動
    private fun runRabbit() {
        Thread {
            val sleepProbability = arrayOf(true, true, false) // 2/3 機率偷懶
            while (progressRabbit < GOAL && progressTurtle < GOAL) {
                try {
                    Thread.sleep(100L)      // 基本步伐
                    if (sleepProbability.random()) {
                        Thread.sleep(300L)  // 偷懶一下
                    }
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

                progressRabbit += 3
                handler.obtainMessage(MSG_RABBIT).sendToTarget()
            }
        }.start()
    }

    // 用 Thread 模擬烏龜移動
    private fun runTurtle() {
        Thread {
            while (progressTurtle < GOAL && progressRabbit < GOAL) {
                try {
                    Thread.sleep(100L)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

                progressTurtle += 1
                handler.obtainMessage(MSG_TURTLE).sendToTarget()
            }
        }.start()
    }
}
