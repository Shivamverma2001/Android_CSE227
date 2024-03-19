package com.example.texttospeech

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import java.util.Locale

class MainActivity : AppCompatActivity() {
    lateinit var mic:ImageView
    lateinit var speechToText:TextView
    val REQUEST_CODE_SPEECH_INPUT=1
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mic=findViewById(R.id.imageView)
        speechToText=findViewById(R.id.textView)

        mic.setOnClickListener {
            val i=Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).
            apply{
                putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                )
                putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE,
                    Locale.getDefault()
                )
                putExtra(
                    RecognizerIntent.EXTRA_PROMPT,
                    "Speak to text"
                )
            }
            try{
                startActivityForResult(i,REQUEST_CODE_SPEECH_INPUT)
            }catch (e:Exception){
                Toast.makeText(this, "${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==REQUEST_CODE_SPEECH_INPUT){
            if(resultCode== RESULT_OK && data!=null){
                val result:ArrayList<String>?=
                    data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                speechToText!!.text=result?.get(0)
            }
        }
    }
}