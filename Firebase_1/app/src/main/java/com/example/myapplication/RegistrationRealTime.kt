package com.example.myapplication

import EmployeeInfo
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class RegistrationRealTime : AppCompatActivity() {
    lateinit var u:EmployeeInfo
    lateinit var etEmail: EditText
    lateinit var etConfPass: EditText
    lateinit var nameuser: EditText
    lateinit var phnum: EditText
    lateinit var address: EditText
    lateinit var employeeInfo: EmployeeInfo
    lateinit var firebaseDatabase :FirebaseDatabase
    lateinit var databaseReference:DatabaseReference
    private lateinit var etPass: EditText
    private lateinit var btnSignUp: Button
    lateinit var tvRedirectLogin: TextView
    // create Firebase authentication object
    private lateinit var auth: FirebaseAuth
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_real_time)
        etEmail = findViewById(R.id.etSEmailAddress)
        etConfPass = findViewById(R.id.etSConfPassword)
        etPass = findViewById(R.id.etSPassword)
        nameuser=findViewById(R.id.etSetName)
        phnum=findViewById(R.id.econtactnum)
        address=findViewById(R.id.enterAddress)
        btnSignUp = findViewById(R.id.btnSSigned)
        employeeInfo= EmployeeInfo()
        u=EmployeeInfo()
        firebaseDatabase=Firebase.database
        databaseReference = firebaseDatabase.
        getReference("EmployeeInfo")
        tvRedirectLogin = findViewById(R.id.tvRedirectLogin)
// Initialising auth object
        auth = Firebase.auth

        btnSignUp.setOnClickListener {
            signUpUser()
        }

// switching from signUp Activity to Login Activity
        tvRedirectLogin.setOnClickListener {
            val intent = Intent(this, LoginRealTime::class.java)
            startActivity(intent)
        }

    }

    private fun signUpUser() {
        val email = etEmail.text.toString()
        val pass = etPass.text.toString()
        val confirmPassword = etConfPass.text.toString()
        val n=nameuser.text.toString()
        val a=address.text.toString()
        val c=phnum.text.toString()

// check pass
        if (email.isBlank() || pass.isBlank() || confirmPassword.isBlank()) {
            Toast.makeText(this, "Email and Password can't be blank", Toast.LENGTH_SHORT).show()
            return
        }

        if (pass != confirmPassword) {
            Toast.makeText(this, "Password and Confirm Password do not match", Toast.LENGTH_SHORT)
                .show()
            return
        }
// If all credential are correct
// We call createUserWithEmailAndPassword
// using auth object and pass the
// email and pass in it.
        auth.createUserWithEmailAndPassword(email, pass).
        addOnCompleteListener(this) {
            if (it.isSuccessful) {
                Toast.makeText(this, "Successfully Singed Up"+it.exception, Toast.LENGTH_SHORT).show()
                addDatatoFirebase(n, c, a)
            } else {
                Toast.makeText(this, "Singed Up Failed!"+it.exception, Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun addDatatoFirebase(n: String, c: String, a: String) {
        employeeInfo.setEmployeeName(n)
        employeeInfo.setEmployeeContactNumber(c)
        employeeInfo.setEmployeeAddress(a)
        val postListener = object : ValueEventListener
        {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
// Get Post object and use the values to update the UI
                databaseReference.setValue(employeeInfo);
// after adding this data we are showing toast message.
                Toast.makeText(applicationContext, "data added",
                    Toast.LENGTH_SHORT).show();
            }
            override fun onCancelled(databaseError: DatabaseError) {
// Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled",
                    databaseError.toException())
                Toast.makeText(applicationContext,
                    "Fail to add data " + databaseError,
                    Toast.LENGTH_SHORT).show();
            }
        }
        databaseReference.addValueEventListener(postListener)
    }
}