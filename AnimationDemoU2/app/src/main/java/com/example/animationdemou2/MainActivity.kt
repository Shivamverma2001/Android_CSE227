package com.example.animationdemou2

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView

class MainActivity : AppCompatActivity() {
    lateinit var iv:ImageView
    lateinit var blink:Button
    lateinit var anim:Animation
    lateinit var rotate:Button
    lateinit var bounce:Button
    lateinit var fadeIn:Button
    lateinit var fadeOut:Button
    lateinit var move:Button
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        blink=findViewById(R.id.button)
        rotate=findViewById(R.id.button2)
        bounce=findViewById(R.id.button3)
        fadeIn=findViewById(R.id.button4)
        fadeOut=findViewById(R.id.button5)
        move=findViewById(R.id.button6)

        iv=findViewById(R.id.iv)
//        val s=AlphaAnimation(0.0f,1.0f)
//
//        s.duration=1000
//        s.repeatMode=Animation.REVERSE
//
//        s.repeatCount=Animation.INFINITE
//        s.startOffset=2000
//
//        iv.startAnimation(s)

        rotate.setOnClickListener {
            anim=AnimationUtils.loadAnimation(this,R.anim.rotate)
            iv.startAnimation(anim)
        }
        blink.setOnClickListener {
            anim=AnimationUtils.loadAnimation(this,R.anim.blink)
            iv.startAnimation(anim)
        }
        bounce.setOnClickListener {
            anim=AnimationUtils.loadAnimation(this,R.anim.bounce)
            iv.startAnimation(anim)
        }
        fadeIn.setOnClickListener {
            anim=AnimationUtils.loadAnimation(this,R.anim.fade_in)
            iv.startAnimation(anim)
        }
        fadeOut.setOnClickListener {
            anim=AnimationUtils.loadAnimation(this,R.anim.fade_out)
            iv.startAnimation(anim)
        }
        move.setOnClickListener {
            anim=AnimationUtils.loadAnimation(this,R.anim.together)
            iv.startAnimation(anim)
        }
    }
}