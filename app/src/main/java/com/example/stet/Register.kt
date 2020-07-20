package com.example.stet

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.stet.R
import kotlinx.android.synthetic.main.register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class Register : AppCompatActivity() {
    private val BASE_URL = "https://stet2020.herokuapp.com/"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)
        val progress = ProgressDialog(this@Register)
        progress.setMessage("Loading  :) ")
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progress.isIndeterminate = true
        progress.show()
        val phone: String = intent.getStringExtra("phone")
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var retrofitInterface: RetrofitInterface = retrofit.create(RetrofitInterface::class.java)
        val map: HashMap<String?, String?> = HashMap()
        map["Phone1"] = phone
        val call1: Call<Personal?>? = retrofitInterface.getPersonal(map)
        call1!!.enqueue(object : Callback<Personal?> {
            override fun onResponse(
                call: Call<Personal?>?,
                response: Response<Personal?>
            ) {
                if (response.code() == 200) {

                    val result = response.body()

                    if (result != null) {

                        register_personal.background = getDrawable(R.drawable.button_shape2)
                    } else {
                        progress.dismiss()
                    }


                }
            }

            override fun onFailure(
                call1: Call<Personal?>?,
                t: Throwable
            ) {
                Log.d("Failure", t.message)
                Toast.makeText(
                    this@Register, t.message,
                    Toast.LENGTH_LONG
                ).show()
            }
        })
        val map2: HashMap<String?, String?> = HashMap()
        map2["Phone"] = phone
        val call2: Call<PreferenceClass?>? = retrofitInterface.getPreferences(map2)
        call2!!.enqueue(object : Callback<PreferenceClass?> {
            override fun onResponse(
                call: Call<PreferenceClass?>?,
                response: Response<PreferenceClass?>
            ) {
                if (response.code() == 200) {

                    val result = response.body()

                    if (result != null) {
                        register_professional.background = getDrawable(R.drawable.button_shape2)
                        progress.dismiss()
                    }


                } else {

                    progress.dismiss()
                }

            }

            override fun onFailure(
                call: Call<PreferenceClass?>?,
                t: Throwable
            ) {
                Log.d("Failure", t.message)
                Toast.makeText(
                    this@Register, t.message,
                    Toast.LENGTH_LONG
                ).show()
            }
        })
        progress.dismiss()
        register_personal.setOnClickListener {
            val i = Intent(this, fourth::class.java)
            i.putExtra("phone", phone)
            startActivity(i)

        }
        register_professional.setOnClickListener {
            val i = Intent(this, five::class.java)
            i.putExtra("phone", phone)
            startActivity(i)

        }
        register_payment.setOnClickListener {
            val i = Intent(this, six::class.java)
            i.putExtra("phone", phone)
            startActivity(i)

        }
        register_documents.setOnClickListener {
            val i = Intent(this, seven::class.java)
            i.putExtra("phone", phone)
            startActivity(i)

        }
        register_submit.setOnClickListener {
            val i = Intent(this, eight::class.java)
            i.putExtra("phone", phone)
            startActivity(i)

        }
        register_back.setOnClickListener {
            val i = Intent(this, third::class.java)
            i.putExtra("phone", phone)
            startActivity(i)
        }
    }
}