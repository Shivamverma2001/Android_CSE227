package com.example.myapplication

import EmployeeInfo
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.core.Context
import com.google.firebase.database.database
import com.google.firebase.database.getValue

class ShowRealTimeData : AppCompatActivity() {
    lateinit var databaseReference: DatabaseReference
    lateinit var retriveData:TextView
    lateinit var firebaseDatabase:FirebaseDatabase
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_real_time_data)
        retriveData=findViewById(R.id.textView4)
        firebaseDatabase= Firebase.database
        databaseReference=firebaseDatabase.
        getReference("EmployeeInfo")
        val employeeListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val employee = dataSnapshot.getValue(String::class.java)
                retriveData!!.text=employee

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        databaseReference.addValueEventListener(employeeListener)
    }
}