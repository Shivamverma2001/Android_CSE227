package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class Otp : AppCompatActivity() {
    lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)

        auth=FirebaseAuth.getInstance()
        val storedVerificationId=intent.getStringExtra("storedVerificationId")

        findViewById<Button>(R.id.button2).setOnClickListener {
            val otp=findViewById<EditText>(R.id.editTextNumber).text.trim().toString()
            if(otp.isNotEmpty()){
                val credential: PhoneAuthCredential= PhoneAuthProvider.getCredential(
                    storedVerificationId.toString(),otp
                )
                signInWithAuthCredential(credential)
            }else{
                Toast.makeText(this, "Enter OTP", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun signInWithAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) {
                task->
                if(task.isSuccessful){
                    val intent= Intent(this,PhoneOtpMain::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    if(task.exception is FirebaseAuthInvalidCredentialsException){
                        Toast.makeText(this, "Invalid OTP", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }
}