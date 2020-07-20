package com.example.stet

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.stet.R
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator
import kotlinx.android.synthetic.main.page_2.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class second : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    var codeSent: String? = null
    lateinit var Email: String
    lateinit var Password: String
    var E: Int = 0
    var P: Int = 0

    var check = 0
    // private val BASE_URL = "http://192.168.43.114:3000"
    private val BASE_URL = "https://stet2020.herokuapp.com/"
    var auth2: FirebaseAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page_2)
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var retrofitInterface: RetrofitInterface = retrofit.create(RetrofitInterface::class.java)

        mAuth = FirebaseAuth.getInstance()
        var name: String = intent.getStringExtra("Name")
        var email: String = intent.getStringExtra("Email")
        var phone: String = intent.getStringExtra("Phone")
        var aadhar: String = intent.getStringExtra("Aadhar")
        var pass: String = intent.getStringExtra("Password")
        Email = email
        Password = pass
        page_2_email.text = "Email ID : " + email
        page_2_get_phn.text = "Phone Number : " + phone
        page_2_aadhar.text = "Aadhar No: " + aadhar
        page_2_phn_verify.visibility = View.INVISIBLE
        page_2_email_verify.visibility = View.INVISIBLE
        page_2_aadhar_verify.visibility = View.INVISIBLE

        page_2_next.setOnClickListener {
            P = 1
            E = 1
            if (P != 1) {
                Toast.makeText(this, "Please Verify Phone ", Toast.LENGTH_LONG).show()
            } else if (E != 1) {
                Toast.makeText(this, "Please Verify Email", Toast.LENGTH_LONG).show()
            } else {
                val progress = ProgressDialog(this)
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER)
                progress.isIndeterminate = true
                progress.setMessage("Registering :) ")
                progress.show()

                val map: HashMap<String, String> = HashMap()

                map["name"] = name
                map["email"] = email
                map["password"] = pass
                map["aadhar"] = aadhar
                map["phone"] = phone
                val call: Call<Void?>? = retrofitInterface.executeSignup(map)
                call!!.enqueue(object : Callback<Void?> {
                    override fun onResponse(
                        call: Call<Void?>?,
                        response: Response<Void?>
                    ) {
                        if (response.code() == 200) {
                            Toast.makeText(
                                this@second,
                                "Signed up successfully", Toast.LENGTH_LONG
                            ).show()
                            progress.dismiss()
                            val i = Intent(this@second, third::class.java)
                            i.putExtra("phone", phone)


                            startActivity(i)
                        } else if (response.code() == 400) {
                            Toast.makeText(
                                this@second,
                                "Already registered", Toast.LENGTH_LONG
                            ).show()
                            progress.dismiss()
                        }
                    }

                    override fun onFailure(
                        call: Call<Void?>?,
                        t: Throwable
                    ) {
                        Toast.makeText(
                            this@second, "Poor Internet Try again",
                            Toast.LENGTH_LONG
                        ).show()
                        progress.dismiss()
                    }
                })

            }
        }

        page_2_back.setOnClickListener {
            val i = Intent(this, ten::class.java)
            startActivity(i)
        }

        page_2_aadhar_verify.setOnClickListener {
            val URL: String = ""
            verifyAadhar(aadhar, URL)
        }


        page_2_send_phn_otp.setOnClickListener {

            //progress.setMessage("Sending OTP :) ")
            //progress.show()
            sendVerificationCode(phone)


        }

        page_2_phn_verify.setOnClickListener {
            // progress3.show()
            verifySignInCode()

        }



        page_2_send_email_link.setOnClickListener {


            emailVal(email, pass)
            page_2_email_verify.setOnClickListener {
                // progress5.show()
                val user = auth2.currentUser
                if (user != null) {
                    if (user.isEmailVerified) {
                        Toast.makeText(this, "Email Verified", Toast.LENGTH_LONG).show()
                        Log.d("TAG", "Verify:success")
                        page_2_email_check.text = "Email Verified"
                        page_2_email_verify.text = "Verified"
                        page_2_send_email_link.visibility = View.INVISIBLE
                        //  progress5.dismiss()
                        E = 1

                    } else {
                        Toast.makeText(this, "Email Not Verified", Toast.LENGTH_LONG)
                            .show()
                        Log.d("TAG", "Verify:failed")
                        //progress5.dismiss()
                    }
                } else {
                    Toast.makeText(this, "Empty User", Toast.LENGTH_LONG)
                        .show()
                    Log.d("TAG", "Empty user")
                    //progress5.dismiss()


                }
            }
        }

    }

    private fun emailVal(eml: String, pass: String) {
        //  progress4.show()
        auth2.createUserWithEmailAndPassword(eml, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {


                    Log.d("TAG", "createUserWithEmail:success")
                    val user = auth2.currentUser

                    user?.sendEmailVerification()?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("TAG", "Email sent.")
                            Toast.makeText(this, "Email sent", Toast.LENGTH_LONG)
                                .show()
                            //progress4.dismiss()
                            page_2_email_verify.visibility = View.VISIBLE
                            page_2_send_email_link.visibility = View.INVISIBLE
                        } else {
                            Log.d("TAG", "Email NOT sent.")
                            Toast.makeText(this, "Email sent failed", Toast.LENGTH_LONG)
                                .show()
                            //  progress4.dismiss()
                            page_2_email_verify.visibility = View.INVISIBLE
                            page_2_send_email_link.visibility = View.VISIBLE
                        }

                    }

                } else {


                    Log.w("TAG", "createUserWithEmail:failure", task.exception)
                    check = 1
                    Toast.makeText(
                        baseContext, "Already exists",
                        Toast.LENGTH_SHORT
                    ).show()
                    // progress4.dismiss()

                }


            }

        page_2_email_verify.setOnClickListener {
            //  progress5.show()
            auth2.signInWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {


                        val user = auth2.currentUser
                        if (user != null && user.isEmailVerified) {
                            Log.d("TAG", "Email Verification :success")
                            Toast.makeText(this, "Email Verified", Toast.LENGTH_LONG).show()
                            page_2_email_check.text = "Email Verified"
                            page_2_email_verify.text = "Verified"
                            E = 1
                            //  progress5.dismiss()
                            page_2_send_email_link.visibility = View.INVISIBLE


                        } else {
                            Toast.makeText(this, "Email Not Verified", Toast.LENGTH_LONG)
                                .show()
                            Log.d("TAG", "Verify:failed")
                            page_2_send_email_link.visibility = View.VISIBLE
                            //   progress5.dismiss()
                        }


                    } else {

                        Log.w("TAG", "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                        // progress5.dismiss()
                        page_2_send_email_link.visibility = View.VISIBLE

                    }


                }
        }
    }


    /* private fun emailValidate() {

         val dynamicLink = Firebase.dynamicLinks.dynamicLink {
             link = Uri.parse("https://stet.page.link/stet")
             domainUriPrefix = "https://myappstet.page.link"
             androidParameters("com.example.android") {

             }
             iosParameters("com.example.ios") {

             }
         }

         val dynamicLinkUri = dynamicLink.uri
         val auth1 = FirebaseAuth.getInstance()
         val user = auth1.currentUser

         val url = "http://myappstet.page.link/verify?uid=" + user?.uid
         val actionCodeSettings = ActionCodeSettings.newBuilder()
             .setUrl(dynamicLinkUri.toString())
             .setHandleCodeInApp(true)
             .setIOSBundleId("com.example.s1.ios")
             .setAndroidPackageName(
                 "com.example.s1.android",
                 true,
                 "12"
             )
             .build()
         val auth = FirebaseAuth.getInstance()
         auth.sendSignInLinkToEmail(page_2_get_email_id.text.toString(), actionCodeSettings)
             .addOnCompleteListener { task ->
                 if (task.isSuccessful) {
                     Toast.makeText(
                         this,
                         "Email Sent to " + page_2_get_email_id.text.toString(),
                         Toast.LENGTH_LONG
                     ).show()
                     page_2_email_verify.text="Email Sent"
                 } else {
                     Toast.makeText(this, task.exception.toString(), Toast.LENGTH_LONG).show()
                 }
             }


         var flag: Boolean = auth.isSignInWithEmailLink(page_2_get_email_id.text.toString())
         if (flag) {
             page_2_email_verify.text = "Verified"

         }
         else
         {
             page_2_email_verify.text = " NOT Verified"
         }
     }
 */

    private fun verifySignInCode() {
        val code: String = page_2_otp_phn.text.toString()
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
                    // progress3.dismiss()
                    page_2_phn.text = "Phone Verifciation Success"
                    page_2_phn_verify.text = "Verified"
                    page_2_enter_otp_phn.visibility = View.INVISIBLE
                    page_2_otp_phn.visibility = View.INVISIBLE
                    page_2_send_phn_otp.visibility = View.INVISIBLE
                    P = 1

                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(
                            applicationContext,
                            "Incorrect Verification Code ", Toast.LENGTH_LONG
                        ).show()
                        page_2_send_phn_otp.visibility = View.VISIBLE
                        page_2_phn_verify.visibility = View.INVISIBLE
                        page_2_send_phn_otp.text = "Resend OTP"
                        // progress3.dismiss()
                    }
                }
            }
    }

    private fun sendVerificationCode(phn: String) {

        val phone: String = "+91$phn"
        if (phone.isEmpty()) {
            page_2_get_phn.requestFocus()
            page_2_get_phn.error = "Field can't be empty"
            return
        }
        if (phone.length < 10) {
            page_2_get_phn.requestFocus()
            page_2_get_phn.error = "Enter complete phone number"
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

    private var mCallbacks: OnVerificationStateChangedCallbacks =
        object : OnVerificationStateChangedCallbacks() {


            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                page_2_phn.text = "Phone Verifciation Success"
                page_2_phn_verify.text = "Verified"
                page_2_enter_otp_phn.visibility = View.INVISIBLE
                page_2_otp_phn.visibility = View.INVISIBLE
                page_2_send_phn_otp.visibility = View.INVISIBLE
                //progress3.dismiss()
            }

            override fun onVerificationFailed(e: FirebaseException) {
                page_2_phn.text = "Failed"
                Log.d("error", e.toString())
                page_2_phn_verify.visibility = View.INVISIBLE
                page_2_send_phn_otp.visibility = View.VISIBLE
                // progress.dismiss()
            }

            override fun onCodeSent(
                s: String,
                forceResendingToken: ForceResendingToken
            ) {
                super.onCodeSent(s, forceResendingToken)
                page_2_phn.text = "OTP sent"
                page_2_phn_verify.visibility = View.VISIBLE
                page_2_send_phn_otp.visibility = View.INVISIBLE
                codeSent = s
                //progress.dismiss()


            }


        }


    private fun validNumber(editText: EditText, d: Int): Int {
        var x = 0
        editText.text.toString().validator()
            .nonEmpty()
            .validNumber()
            .onlyNumbers()
            .maxLength(d)
            .minLength(d)
            .addErrorCallback {
                editText.error = "Enter $d digit Number Only"
                x = 1
            }
            .addSuccessCallback {
                x = 0

            }
            .check()
        return x
    }

    private fun validEmail(editText: EditText): Int {
        var x = 0
        editText.text.toString().validator()
            .validEmail()
            .nonEmpty()
            .addErrorCallback {
                editText.error = "Enter email in correct format eg. xyz@gmail.com"
                x = 1
            }
            .addSuccessCallback {
                x = 0

            }
            .check()
        return x
    }

    private fun verifyAadhar(aadhar: String, URL: String) {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var retrofitInterface2: Retro2? = retrofit.create(Retro2::class.java)
        val map: HashMap<String, String> = HashMap()
        map["username"] = aadhar
        val call: Call<Void?>? = retrofitInterface2?.checkAadhar(map)
        call!!.enqueue(object : Callback<Void?> {
            override fun onResponse(
                call: Call<Void?>?,
                response: Response<Void?>
            ) {
                if (response.code() == 200) {
                    Toast.makeText(
                        this@second,
                        "Aadhar validation Complete", Toast.LENGTH_LONG
                    ).show()
                    val call1: Call<Body1?>? =
                        retrofitInterface2?.getPhn(aadhar) //idhar doubt hai ki ye phone number return karega ki nhi
                    call1!!.enqueue(object : Callback<Body1?> {
                        override fun onResponse(
                            call1: Call<Body1?>?,
                            response1: Response<Body1?>
                        ) {
                            if (response1.code() == 200) {
                                var bd: Body1 = response1.body()!!
                                Toast.makeText(
                                    this@second,
                                    bd.phoneNumber, Toast.LENGTH_LONG
                                ).show()
                                val map1: HashMap<String, String?> = HashMap()
                                map1["messengerId"] = "8192836451"
                                map1["phoneNumber"] = bd.phoneNumber
                                val call2: Call<Void?>? = retrofitInterface2?.getotp(map1)
                                call2!!.enqueue(object : Callback<Void?> {
                                    override fun onResponse(p0: Call<Void?>, p1: Response<Void?>) {
                                        if (p1.code() == 200) {
                                            Toast.makeText(
                                                this@second,
                                                "OTP Generated ", Toast.LENGTH_LONG
                                            ).show()
                                            val call3: Call<Void?>? =
                                                retrofitInterface2?.matchotp(page_2_aadhar_otp.text.toString())
                                            call3!!.enqueue(object : Callback<Void?> {
                                                override fun onResponse(
                                                    p00: Call<Void?>,
                                                    p11: Response<Void?>
                                                ) {
                                                    if (p11.code() == 200) {
                                                        Toast.makeText(
                                                            this@second,
                                                            "Aadhar Verified ", Toast.LENGTH_LONG
                                                        ).show()
                                                    } else {
                                                        Toast.makeText(
                                                            this@second,
                                                            "Aadhar Not Verified ",
                                                            Toast.LENGTH_LONG
                                                        ).show()
                                                    }
                                                }

                                                override fun onFailure(
                                                    p00: Call<Void?>,
                                                    t4: Throwable
                                                ) {
                                                    Toast.makeText(
                                                        this@second, t4.message,
                                                        Toast.LENGTH_LONG
                                                    ).show()

                                                }
                                            })


                                        }
                                    }

                                    override fun onFailure(p0: Call<Void?>, t3: Throwable) {
                                        Toast.makeText(
                                            this@second, t3.message,
                                            Toast.LENGTH_LONG
                                        ).show()

                                    }
                                })


                            }
                        }

                        override fun onFailure(
                            call1: Call<Body1?>?,
                            t2: Throwable
                        ) {
                            Toast.makeText(
                                this@second, t2.message,
                                Toast.LENGTH_LONG
                            ).show()

                        }
                    })
                }
            }

            override fun onFailure(
                call: Call<Void?>?,
                t1: Throwable
            ) {
                Toast.makeText(
                    this@second, t1.message,
                    Toast.LENGTH_LONG
                ).show()

            }
        })


    }

}
