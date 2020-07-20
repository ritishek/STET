package com.example.stet

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.stet.R
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.page_8.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*
import java.util.concurrent.TimeUnit

class eight : AppCompatActivity() {
    private val BASE_URL = "https://stet2020.herokuapp.com/"
    private var mAuth: FirebaseAuth? = null
    var codeSent: String? = null
    var P = 0
    var auth2: FirebaseAuth = FirebaseAuth.getInstance()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page_8)
        page_8_progress_bar.progress = 100
        var phone: String = intent.getStringExtra("phone")
        mAuth = FirebaseAuth.getInstance()
        page_8_submit.setOnClickListener {
            if (P == 1) {
                Toast.makeText(this, "Registration Successfull", Toast.LENGTH_SHORT).show()
                val map: HashMap<String?, String?> = HashMap()
                val current = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                val formatted = current.format(formatter)
                map["Phone"] = phone
                map["Date"] = formatted
                val retrofit: Retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                var retrofitInterface: RetrofitInterface =
                    retrofit.create(RetrofitInterface::class.java)
                val call2: Call<Void?>? = retrofitInterface.timings(map)
                call2!!.enqueue(object : Callback<Void?> {
                    override fun onResponse(
                        call: Call<Void?>?,
                        response: Response<Void?>
                    ) {
                        if (response.code() == 200) {

                            val i = Intent(this@eight, third::class.java)
                            i.putExtra("phone", phone)
                            startActivity(i)
                        }
                    }

                    override fun onFailure(
                        call: Call<Void?>?,
                        t: Throwable
                    ) {
                        Log.d("Failure", t.message)
                        Toast.makeText(
                            this@eight, t.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })


            }
        }
        page_8_sendotp.setOnClickListener {
            sendVerificationCode(phone)
        }
        page_8_verify.setOnClickListener {
            if (codeSent != null) {
                verifySignInCode()
            }
        }

    }

    private fun verifySignInCode() {
        val code: String = page_8_enter_otp.text.toString()
        val credential = PhoneAuthProvider.getCredential(codeSent!!, code)
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mAuth!!.signInWithCredential(credential)
            .addOnCompleteListener(
                this
            ) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        applicationContext,
                        "Verification Successful",
                        Toast.LENGTH_LONG
                    ).show()
                    page_8_verify.text = "VERIFIED"
                    P = 1
                    page_8_verify.background = getDrawable(R.drawable.button_shape2)
                    page_8_enter_otp.visibility = View.INVISIBLE
                    page_8_sendotp.visibility = View.INVISIBLE
                    page_8_otp.visibility = View.INVISIBLE


                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(
                            applicationContext,
                            "Incorrect Verification Code ", Toast.LENGTH_LONG
                        ).show()

                    }
                }
            }
    }

    private fun sendVerificationCode(phn: String) {

        val phone = "+91$phn"
        if (phone.isEmpty()) {
            return
        }
        if (phone.length < 10) {
            return
        }
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phone,
            60,
            TimeUnit.SECONDS,
            this,
            mCallbacks
        )

    }

    private var mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks =
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {


            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {

            }

            override fun onVerificationFailed(e: FirebaseException) {
                Log.d("error", e.toString())
            }

            override fun onCodeSent(
                s: String,
                forceResendingToken: PhoneAuthProvider.ForceResendingToken
            ) {
                super.onCodeSent(s, forceResendingToken)
                codeSent = s
                page_8_sendotp.text = "RESEND OTP"
            }
        }
}
