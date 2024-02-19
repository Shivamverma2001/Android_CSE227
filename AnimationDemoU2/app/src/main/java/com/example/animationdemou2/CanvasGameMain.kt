package com.example.animationdemou2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class CanvasGameMain : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val obj=GameViewDemo(this)
        setContentView(obj)
    }
}