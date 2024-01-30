package com.example.firebase_2

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    lateinit var save:Button
    lateinit var fetch:Button
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        save=findViewById(R.id.button)
        fetch=findViewById(R.id.button2)

        save.setOnClickListener {
            val i=Intent(this,SaveData::class.java)
            startActivity(i)
        }

        fetch.setOnClickListener {
            val i=Intent(this,ShowDetails::class.java)
            startActivity(i)
        }
    }
}