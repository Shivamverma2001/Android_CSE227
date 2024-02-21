package com.example.mapdemou3

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.Locale

class OpenMap : AppCompatActivity() {

    lateinit var tvlat: TextView
    lateinit var tvlong: TextView
    lateinit var tvCn: TextView
    lateinit var tvLocality: TextView
    lateinit var tvAds: TextView
    lateinit var btn: Button

    private var lat: Double = 0.0
    private var long: Double = 0.0
    lateinit var openbtn: Button

    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private val permissionId = 2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_map)

        tvlat = findViewById(R.id.textView2)
        tvlong = findViewById(R.id.textView3)
        tvCn = findViewById(R.id.textView4)
        tvLocality = findViewById(R.id.textView5)
        tvAds = findViewById(R.id.textView6)

        btn = findViewById(R.id.btn)
        openbtn = findViewById(R.id.mapbtn)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        btn.setOnClickListener {
            getLocation()
        }

        openbtn.setOnClickListener {
            openmap()
        }

    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    @SuppressLint("MissingPermission", "SetTextI18n")

    private fun getLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
// Log.d("Location","Lat-${location.latitude} Lon-${location.longitude}")
                    location?.let {
                        val geocoder = Geocoder(this, Locale.getDefault())
                        val list: List<Address> =
                            geocoder.getFromLocation(
                                location.latitude, location.longitude,
                                1
                            )!!

                        lat = list[0].latitude
                        long = list[0].longitude
                        tvlat.text = "Latitude\n${list[0].latitude}"
                        tvlong.text = "Longitude\n${list[0].longitude}"
                        tvCn.text = "Country Name\n${list[0].countryName}"
                        tvLocality.text = "Locality\n${list[0].locality}"
                        tvAds.text = "Address\n${list[0].getAddressLine(0)}"
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
        return locationManager.isProviderEnabled((LocationManager.GPS_PROVIDER)) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ),
            permissionId
        )
    }

    @SuppressLint("MissingSuperCall")

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == permissionId) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation()
            }
        }
    }

    private fun openmap() {
        val uri = Uri.parse("geo:31.2536,75.7037")
        val mapIntent = Intent(Intent.ACTION_VIEW, uri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }
}