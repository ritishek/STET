package com.example.stet

import android.app.ProgressDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.stet.R
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator
import kotlinx.android.synthetic.main.page_5.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*


class five : AppCompatActivity() {

    // private val BASE_URL = "http://192.168.43.114:3000"
    private val BASE_URL = "https://stet2020.herokuapp.com/"

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page_5)
        page_5_progress_bar.progress = 40
        val phone: String = intent.getStringExtra("phone")

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var retrofitInterface: RetrofitInterface = retrofit.create(RetrofitInterface::class.java)
        val map: HashMap<String?, String?> = HashMap()
        map["Phone"] = phone
        val progress = ProgressDialog(this)
        progress.setMessage("Loading Professional Details  :) ")
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progress.isIndeterminate = true
        progress.show()
        val call2: Call<PreferenceClass?>? = retrofitInterface.getPreferences(map)
        call2!!.enqueue(object : Callback<PreferenceClass?> {
            override fun onResponse(
                call: Call<PreferenceClass?>?,
                response: Response<PreferenceClass?>
            ) {
                if (response.code() == 200) {

                    val result = response.body()

                    if (result != null) {
                        page_5_spin_category.setSelection(getSpinApplicationCategory(result.ApplicationCategory))
                        page_5_spin_language.setSelection(getSpinLanguage(result.PaperLanguage))
                        page_5_next.text = "Update"
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
                    this@five, "Poor Internet Try again",
                    Toast.LENGTH_LONG
                ).show()
                progress.dismiss()
            }
        })
        progress.dismiss()
        val call3: Call<Education?>? = retrofitInterface.getEducation(map)
        call3!!.enqueue(object : Callback<Education?> {
            override fun onResponse(
                call: Call<Education?>?,
                response: Response<Education?>
            ) {
                if (response.code() == 200) {

                    val result = response.body()

                    if (result != null) {
                        page_5_spin_min_qualification.setSelection(getSpinMinaqual(result.MinQualification))
                        page_5_spin_prof_qualification.setSelection(getSpinProfessQual(result.ProfessionalQualification))
                        page_5_enter_university.setText(result.University)
                        page_5_enter_percentage.setText(result.Percentage)
                        page_5_next.text = "Update"
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
                    this@five, "Poor Internet Try again",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
        progress.dismiss()
        page_5_next.setOnClickListener {
            val progress = ProgressDialog(this)
            progress.setMessage("Storing Professional Details  :) ")
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER)
            progress.isIndeterminate = true
            progress.show()
            if (validPercentage(page_5_enter_percentage, 4) == 0
                && validUniversity(page_5_enter_university) == 0
                && validSpinner(page_5_spin_category) == 0
                && validSpinner(page_5_spin_language) == 0
                && validSpinner(page_5_spin_min_qualification) == 0
                && validSpinner(page_5_spin_prof_qualification) == 0
                && page_5_chechbox.isChecked
            ) {
                val myFirstDocument: HashMap<String, String> = HashMap()
                val myFirstDocument2: HashMap<String, String> = HashMap()
                myFirstDocument["Phone"] = phone
                myFirstDocument["Percentage"] = page_5_enter_percentage.text.toString()
                myFirstDocument["University"] = page_5_enter_university.text.toString()
                myFirstDocument["MinQualification"] =
                    page_5_spin_min_qualification.selectedItem.toString()
                myFirstDocument["ProfessionalQualification"] =
                    page_5_spin_prof_qualification.selectedItem.toString()
                myFirstDocument2["Phone"] = phone
                myFirstDocument2["ApplicationCategory"] =
                    page_5_spin_category.selectedItem.toString()
                myFirstDocument2["PaperLanguage"] = page_5_spin_language.selectedItem.toString()
                val call: Call<Void?>? = retrofitInterface.executeEducation(myFirstDocument)
                call!!.enqueue(object : Callback<Void?> {
                    override fun onResponse(
                        call: Call<Void?>?,
                        response: Response<Void?>
                    ) {
                        if (response.code() == 200) {
                            Log.d("Success", "Data Stored")
                            Toast.makeText(
                                this@five,
                                "Data stored successfully", Toast.LENGTH_LONG
                            ).show()

                        } else {

                        }

                    }

                    override fun onFailure(
                        call: Call<Void?>?,
                        t: Throwable
                    ) {
                        Log.d("Failure", t.message)
                        Toast.makeText(
                            this@five, "Poor Internet Try again",
                            Toast.LENGTH_LONG
                        ).show()


                    }
                })
                val call2: Call<Void?>? = retrofitInterface.executePreference(myFirstDocument2)
                call2!!.enqueue(object : Callback<Void?> {
                    override fun onResponse(
                        call2: Call<Void?>?,
                        response: Response<Void?>
                    ) {
                        if (response.code() == 200) {
                            Log.d("Success", "Data Stored")
                            Toast.makeText(
                                this@five,
                                "Data stored successfully", Toast.LENGTH_LONG
                            ).show()

                            val i = Intent(this@five, Register::class.java)
                            progress.dismiss()
                            i.putExtra("phone", phone)
                            startActivity(i)
                        } else {
                            progress.dismiss()
                        }
                    }

                    override fun onFailure(
                        call: Call<Void?>?,
                        t: Throwable
                    ) {
                        Log.d("Failure", t.message)
                        Toast.makeText(
                            this@five, "Poor Internet Try again",
                            Toast.LENGTH_LONG
                        ).show()
                        progress.dismiss()
                    }
                })
            } else {
                if (page_5_chechbox.isChecked) {

                    Toast.makeText(this, "Please check errors", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Accept T&C", Toast.LENGTH_SHORT).show()
                }
                progress.dismiss()

            }

        }
        page_5_back.setOnClickListener {
            val i = Intent(this, Register::class.java)
            i.putExtra("phone", phone)
            startActivity(i)


        }

    }

    private fun validUniversity(editText: EditText): Int {
        var x = 0
        editText.text.toString().validator()
            .nonEmpty()
            .noSpecialCharacters()
            .noNumbers()
            .addErrorCallback {
                editText.error = "Enter valid University format"
                x = 1
            }
            .addSuccessCallback {
                x = 0
            }
            .check()
        return x
    }

    private fun validPercentage(editText: EditText, d: Int): Int {
        var x = 0
        editText.text.toString().validator()
            .nonEmpty()
            .validNumber()
            .maxLength(d + 1)
            .minLength(2)
            .addErrorCallback {
                editText.error = "Enter upto $d digit Number Only"
                x = 1
            }
            .addSuccessCallback {
                x = 0

            }
            .check()
        return x
    }

    private fun validSpinner(Spinner1: Spinner): Int {
        var x = 0
        if (Spinner1.selectedItem.toString().trim() == "Select") {
            x = 1
            Toast.makeText(this, "Select Atleast one", Toast.LENGTH_LONG).show()
        }

        return x
    }

    private fun getSpinApplicationCategory(str: String?): Int {

        val list = resources.getStringArray(R.array.application_category)
        val i = 1
        for (i in 1..3) {
            if (list[i] == str) {
                return i
            }
        }
        return 0
    }

    private fun getSpinLanguage(str: String?): Int {

        val list = resources.getStringArray(R.array.language_choice)
        val i = 1
        for (i in 1..5) {
            if (list[i] == str) {
                return i
            }
        }
        return 0
    }

    private fun getSpinMinaqual(str: String?): Int {

        val list = resources.getStringArray(R.array.min_qualification)
        val i = 1
        for (i in 1..2) {
            if (list[i] == str) {
                return i
            }
        }
        return 0
    }

    private fun getSpinProfessQual(str: String?): Int {

        val list = resources.getStringArray(R.array.prof_qualification)
        val i = 1
        for (i in 1..4) {
            if (list[i] == str) {
                return i
            }
        }
        return 0
    }
}
