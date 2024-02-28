package com.example.mapdemou3

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.*

class GeoCoder : AppCompatActivity() {
    lateinit var tvLatitude: TextView
    lateinit var tvLongitude:TextView
    lateinit var tvCountryName:TextView
    lateinit var tvLocality:TextView
    lateinit var tvAddress:TextView
    lateinit var btnLocation: Button
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private val permissionId = 2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_geo_coder)
        tvLatitude = findViewById(R.id.tv_latitude)
        tvLongitude = findViewById(R.id.tv_longitude)
        tvCountryName = findViewById(R.id.tv_countryName)
        tvLocality = findViewById(R.id.tv_Locality)
        tvAddress = findViewById(R.id.tv_address)
        btnLocation = findViewById(R.id.btn_location)
        mFusedLocationClient = LocationServices.
        getFusedLocationProviderClient(this)
        btnLocation.setOnClickListener {
            getLocation()
        }
    }
    @SuppressLint("MissingPermission", "SetTextI18n")
    private fun getLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.lastLocation.
                addOnSuccessListener {
                        location: Location? ->
// Log.d("Location","Lat-${location.latitude} Lon-${location.longitude}")
                    location?.let {
                        val geocoder = Geocoder(this, Locale.getDefault())
                        val list: List<Address> =
                            geocoder.getFromLocation(location.latitude, location.longitude,
                                1)!!
                        tvLatitude.text = "Latitude\n${list[0].latitude}"
                        tvLongitude.text = "Longitude\n${list[0].longitude}"
                        tvCountryName.text = "Country Name\n${list[0].countryName}"
                        tvLocality.text = "Locality\n${list[0].locality}"
                        tvAddress.text = "Address\n${list[0].getAddressLine(0)}"
                    }
                }
            } else {
                Toast.makeText(this, "Please turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }
    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }
    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }
    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            permissionId
        )
    }
    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == permissionId) {
            if ((grantResults.isNotEmpty() && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED)) {
                getLocation()
            }
        }
    }


}