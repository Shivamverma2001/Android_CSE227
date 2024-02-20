package com.example.animationdemou2

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Fade
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat

class TransitionDemo : AppCompatActivity() {
    lateinit var imageView:ImageView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transition_demo)
        imageView=findViewById(R.id.imageView2)
        val fade= Fade()
        fade.duration=10000

        window.enterTransition=fade
        window.exitTransition=fade

        imageView.setOnClickListener{
            val i=Intent(this@TransitionDemo,CanvasGameMain::class.java)

            val options=ActivityOptionsCompat.
            makeSceneTransitionAnimation(
                this@TransitionDemo,imageView,ViewCompat.getTransitionName(imageView)!!
            )
            startActivity(i,options.toBundle())
        }
    }
}