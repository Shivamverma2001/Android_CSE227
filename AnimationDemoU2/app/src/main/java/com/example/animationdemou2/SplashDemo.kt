package com.example.animationdemou2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView

class SplashDemo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_demo)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val backImage:ImageView=findViewById(R.id.imageView)
        val slideAnimation=AnimationUtils.loadAnimation(this,R.anim.together)
        backImage.startAnimation(slideAnimation)
        Handler().postDelayed({
            val i=Intent(this,MainActivity::class.java)
            startActivity(i)
            finish()
        },3000)
    }
}