package com.example.stet

//import android.R
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.stet.R
import kotlinx.android.synthetic.main.page_3.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class third : AppCompatActivity() {

    lateinit var Phone: String
    //private val BASE_URL = "http://192.168.43.114:3000"
    private val BASE_URL = "https://stet2020.herokuapp.com/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page_3)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar!!.title = "STET APPLICATION"
        val phone: String = intent.getStringExtra("phone")
        Phone = phone
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var retrofitInterface: RetrofitInterface = retrofit.create(RetrofitInterface::class.java)
        val map: HashMap<String?, String?> = HashMap()

        map["phone"] = phone

        val call: Call<Important?>? =
            retrofitInterface.getDetails(map)
        call!!.enqueue(object : Callback<Important?> {
            override fun onResponse(
                call: Call<Important?>,
                response: Response<Important?>
            ) {
                var result = response.body()
                if (response.code() == 200) {
                    Log.d("1", "2")
                    if (result != null) {

                    }
                } else if (response.code() == 404) {
                    Toast.makeText(this@third, "Not Found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(
                call: Call<Important?>,
                t: Throwable
            ) {
                Log.d("0", t.message)
                call.cancel()
            }
        })
        page_3_register.setOnClickListener {
            val i = Intent(this, Register::class.java)
            i.putExtra("phone", phone)
            startActivity(i)

        }


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {

                val it = Intent(this, home::class.java)
                it.putExtra("phone", Phone)
                startActivity(it)
                return true
            }
            R.id.status -> {
                val it = Intent(this, Status::class.java)
                it.putExtra("phone", Phone)
                startActivity(it)
                return true
            }
            R.id.Help -> {

                val it = Intent(this, Help::class.java)
                it.putExtra("phone", Phone)
                startActivity(it)
                return true
            }
            R.id.FAQs -> {

                val it = Intent(this, FAQS::class.java)
                it.putExtra("phone", Phone)
                startActivity(it)

                return true
            }
            R.id.timeline -> {
                val it = Intent(this, Timeline::class.java)
                it.putExtra("phone", Phone)
                startActivity(it)

                return true
            }
            R.id.logout -> {
                val sharedPreferences = getSharedPreferences(
                    "MySharedPref",
                    Context.MODE_PRIVATE
                )
                val myEdit = sharedPreferences.edit()
                myEdit.putBoolean("login", false).apply()
                myEdit.putString("phone", "").apply()
                val i = Intent(this@third, MainActivity::class.java)
                startActivity(i)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
