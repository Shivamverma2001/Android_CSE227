package com.example.animationdemou2

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class GameTransition : AppCompatActivity() {
    lateinit var transition:Button
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_transition)
        transition=findViewById(R.id.button7)
        transition.setOnClickListener {
            startActivity(Intent(this,CanvasGameMain::class.java))
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out)
        }

    }
}