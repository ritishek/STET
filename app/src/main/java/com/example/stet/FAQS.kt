package com.example.stet

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.stet.R
import kotlinx.android.synthetic.main.faqs.*

class FAQS : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.faqs)
        val phone: String = intent.getStringExtra("phone")
        qus1.setOnTouchListener { v, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> ans1.visibility = View.VISIBLE
            }

            v?.onTouchEvent(event) ?: true
        }
        qus2.setOnTouchListener { v, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> ans2.visibility = View.VISIBLE
            }

            v?.onTouchEvent(event) ?: true
        }
        qus3.setOnTouchListener { v, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> ans3.visibility = View.VISIBLE
            }

            v?.onTouchEvent(event) ?: true
        }
        qus4.setOnTouchListener { v, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> ans4.visibility = View.VISIBLE
            }
            v?.onTouchEvent(event) ?: true
        }
        faqs_back.setOnClickListener {
            val i = Intent(this, third::class.java)
            i.putExtra("phone", phone)
            startActivity(i)

        }
    }
}