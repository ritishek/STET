package com.example.stet

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.stet.R
import kotlinx.android.synthetic.main.help.*

class Help : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.help)
        val phone: String = intent.getStringExtra("phone")
        help_back.setOnClickListener {
            val i = Intent(this, third::class.java)
            i.putExtra("phone", phone)
            startActivity(i)
        }

    }
}