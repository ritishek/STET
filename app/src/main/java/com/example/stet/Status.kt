package com.example.stet

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.stet.R
import kotlinx.android.synthetic.main.status.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class Status : AppCompatActivity() {

    private val BASE_URL = "https://stet2020.herokuapp.com/"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.status)
        val phone: String = intent.getStringExtra("phone")
        status_back.setOnClickListener {
            val i = Intent(this, third::class.java)
            i.putExtra("phone", phone)
            startActivity(i)

        }

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
                        status_view_aadhar.text = result.Aadhar
                        status_view_address.text = result.AddressOne
                        status_view_candidate.text =
                            result.Fname + " " + result.Mname + " " + result.Lname
                        status_view_mother.text =
                            result.MFname + " " + result.MMname + " " + result.MLname
                        status_view_father.text =
                            result.FHFname + " " + result.FHMname + " " + result.FHLname
                        status_view_dist.text = result.DistrictOne
                        status_view_dob.text = result.DOB
                        status_view_gender.text = result.gender
                        status_view_state.text = result.StateOne
                        status_view_email.text = result.Email1
                        status_view_mobile.text = result.Phone1
                        staus_view_pin.text = result.PinCodeOne
                        status_view_father_husband.text = result.FH
                        status_view_address2.text =
                            result.AddressTwo + " " + result.DistrictTwo + " " + result.StateTwo
                        status_view_community.text = result.Category
                        status_view_mobile2.text = result.Phone2
                        status_view_email2.text = result.Email2


                    }


                }
            }

            override fun onFailure(
                call1: Call<Personal?>?,
                t: Throwable
            ) {
                Log.d("Failure", t.message)
                Toast.makeText(
                    this@Status, t.message,
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
                        status_application_view_category.text = result.ApplicationCategory
                        status_application_view_paper_choice.text = result.PaperLanguage
                    }


                } else {


                }

            }

            override fun onFailure(
                call: Call<PreferenceClass?>?,
                t: Throwable
            ) {
                Log.d("Failure", t.message)
                Toast.makeText(
                    this@Status, t.message,
                    Toast.LENGTH_LONG
                ).show()
            }
        })
        val map3: HashMap<String?, String?> = HashMap()
        map3["Phone"] = phone
        val call3: Call<Education?>? = retrofitInterface.getEducation(map3)
        call3!!.enqueue(object : Callback<Education?> {
            override fun onResponse(
                call: Call<Education?>?,
                response: Response<Education?>
            ) {
                if (response.code() == 200) {

                    val result = response.body()

                    if (result != null) {
                        status_application_view_percentage.text = result.Percentage
                        status_application_view_uni.text = result.University
                        status_application_view_prof_quali.text = result.ProfessionalQualification
                        status_application_view_min_quali.text = result.MinQualification


                    }


                } else {

                }
            }

            override fun onFailure(
                call: Call<Education?>?,
                t: Throwable
            ) {
                Log.d("Failure", t.message)
                Toast.makeText(
                    this@Status, t.message,
                    Toast.LENGTH_LONG
                ).show()
            }
        })


    }

}