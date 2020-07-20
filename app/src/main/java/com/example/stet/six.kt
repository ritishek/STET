package com.example.stet

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import kotlinx.android.synthetic.main.page_6.*
import org.json.JSONObject

class six : Activity(), PaymentResultListener {
    val TAG:String = six::class.toString()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page_6)
        Checkout.preload(applicationContext)
        val phone: String = intent.getStringExtra("phone")
        page_6_progress_bar.progress = 60
        page_6_next.setOnClickListener {
            val i = Intent(this, Register::class.java)
            i.putExtra("phone", phone)
            startActivity(i)
        }
        page_6_back.setOnClickListener {
            val i = Intent(this, Register::class.java)
            i.putExtra("phone", phone)
            startActivity(i)
        }
        page_6_pay.setOnClickListener {
            val fees=10000
            val activity: Activity = this
            val co = Checkout()
            try {
                val options = JSONObject()
                options.put("name","STET Application")
                options.put("description","Registration Fee Charges")
                options.put("image","https://s3.amazonaws.com/rzp-mobile/images/rzp.png")
                options.put("currency","INR")
                options.put("amount",fees)
                val prefill = JSONObject()
                prefill.put("email","9198239087r@gmail.com")
                prefill.put("contact","6387012615")
                options.put("prefill",prefill)
                co.open(activity,options)
            }catch (e: Exception){
                Toast.makeText(activity,"Error in payment: "+ e.message,Toast.LENGTH_LONG).show()
                e.printStackTrace()
            }
        }

    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(this, "Payment Code $p0 Payment Error $p1",Toast.LENGTH_SHORT).show()
    }

    override fun onPaymentSuccess(p0: String?) {
        Toast.makeText(this, "Payment Success $p0",Toast.LENGTH_SHORT).show()
    }
}

