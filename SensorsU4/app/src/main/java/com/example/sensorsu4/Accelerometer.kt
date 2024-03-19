package com.example.sensorsu4

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.getSystemService

class Accelerometer : AppCompatActivity() {
    private var sm:SensorManager?=null
    private var textView:TextView?=null
    private var list:List<Sensor>?=null
    private  val sel:SensorEventListener=object:SensorEventListener{
        @SuppressLint("SetTextI18n")
        override fun onSensorChanged(event: SensorEvent?) {
            val values= event?.values
            textView?.text="x: ${values?.get(0)}\ny: ${values?.get(1)}\nx: ${values?.get(2)}"
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        }

    }
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accelerometer)

        //Get SensorManager instance
        sm=getSystemService(Context.SENSOR_SERVICE) as SensorManager
        textView=findViewById(R.id.textView2)
        list=sm?.getSensorList(Sensor.TYPE_ACCELEROMETER)
        if(list?.isNotEmpty()==true){
            sm?.registerListener(sel, list?.get(0),SensorManager.SENSOR_DELAY_NORMAL)
        }else{
            Toast.makeText(baseContext, "Error: No Accelerometer", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStop() {
        if(list?.isNotEmpty()==true){
            sm?.unregisterListener(sel)
        }
        super.onStop()
    }
}