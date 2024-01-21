package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class PhoneOtpMain : AppCompatActivity() {
    var number:String=""
    lateinit var  auth:FirebaseAuth
    lateinit var storedVerificationId:String
    lateinit var resendToken:PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks:
            PhoneAuthProvider.OnVerificationStateChangedCallbacks

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_otp_main)

        auth=FirebaseAuth.getInstance()
        findViewById<Button>(R.id.button).setOnClickListener {
            login()
        }

        callbacks=object :PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                startActivity(Intent(applicationContext,AfterOtp::class.java))
                finish()
                Log.d("CSE227","onVerificationCompleted Successfully")
            }

            override fun onVerificationFailed(p0: FirebaseException) {

                Log.d("CSE227","onVerificationFailed $p0");
            }

            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                Log.d("CSE227","onCodeSen: $p0")
                storedVerificationId=p0
                resendToken=p1
                Toast.makeText(applicationContext, "In onCodeSent", Toast.LENGTH_SHORT).show()
                val intent=Intent(applicationContext,Otp::class.java)
                intent.putExtra("storedVerificationId",storedVerificationId)
                finish()
            }
        }
    }

    private fun login() {
        number=findViewById<EditText>(R.id.editTextPhone).text.trim().toString()

        if(number.isNotEmpty()){
            number="+91$number"
            sendVerificationCode(number)
        }else{
            Toast.makeText(this, "Enter your mobile number", Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendVerificationCode(number: String) {
        val options=PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(number)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
        Log.d("CSE227","Auth started")
    }
}