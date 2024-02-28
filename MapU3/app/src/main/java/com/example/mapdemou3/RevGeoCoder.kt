package com.example.mapdemou3

import android.annotation.SuppressLint
import android.location.Address
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class RevGeoCoder : AppCompatActivity() {

    lateinit var et :EditText
    lateinit var btn: Button
    lateinit var tvlat : TextView
    lateinit var tvlong : TextView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rev_geo_coder)
        et = findViewById(R.id.et)
        btn = findViewById(R.id.button)
        tvlat = findViewById(R.id.tvlat)
        tvlong = findViewById(R.id.tvlong)

        btn.setOnClickListener {
            if(et.text.toString().isEmpty()){
                Toast.makeText(this,"Enter address",Toast.LENGTH_LONG).show()
            }else{
                getLocationFromAddress(et.text.toString())
            }
        }

    }

    private fun getLocationFromAddress(location: String) {
        val geocoder = Geocoder(this)
        val list: List<Address> = geocoder.getFromLocationName(location,5)!!

        if(list.isNullOrEmpty()){
            return
        }

        tvlat.text = "latitude: ${list[0].latitude}"
        tvlong.text = "longitude: ${list[0].longitude}"

    }


}