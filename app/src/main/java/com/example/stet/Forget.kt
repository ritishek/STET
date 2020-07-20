package com.example.stet
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.stet.R
import com.example.stet.ResetPassword
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.forget.*
import java.util.concurrent.TimeUnit

class Forget : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    var codeSent: String? = null
    var P = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.forget)
        page_forget_sendotp.setOnClickListener {
            sendVerificationCode(page_forget_phn_et.text.toString())
        }
        page_forget_verify.setOnClickListener {
            if (codeSent != null) {
                verifySignInCode()
            }
        }
        page_forget_submit.setOnClickListener {
            if (P == 1) {
                var i = Intent(this, ResetPassword::class.java)
                i.putExtra("Phone", page_forget_phn_et.text.toString())
                startActivity(i)
            }
        }


    }

    private fun verifySignInCode() {
        val code: String = page_forget_enter_otp.text.toString()
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
                    page_forget_verify.text = "VERIFIED"
                    P = 1
                    page_forget_verify.background = getDrawable(R.drawable.button_shape2)
                    page_forget_enter_otp.visibility = View.INVISIBLE
                    page_forget_sendotp.visibility = View.INVISIBLE
                    page_forget_otp.visibility = View.INVISIBLE


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
                P = 1
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
                page_forget_sendotp.text = "RESEND OTP"
            }
        }
}