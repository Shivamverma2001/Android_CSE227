package com.example.texttospeech

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.speech.tts.TextToSpeech
import android.util.Log
import java.util.Locale

class TextToSpeech : AppCompatActivity(),TextToSpeech.OnInitListener {
    lateinit var eText:EditText
    lateinit var tText:TextView
    var tts:TextToSpeech?=null
    lateinit var btn:Button
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_to_speech)
        eText=findViewById(R.id.editTextText)
        tText=findViewById(R.id.textView2)
        btn=findViewById(R.id.button)

        tts= TextToSpeech(this,this)
        btn!!.setOnClickListener { speakOut() }
    }
    override fun onInit(status:Int){
        if(status == TextToSpeech.SUCCESS){
            val result=tts!!.setLanguage(Locale.US)
            if(result==TextToSpeech.LANG_MISSING_DATA || result==TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS","The language not supported")
            }else{
                btn!!.isEnabled=true
            }
        }
    }

    private fun speakOut() {
        val text=eText!!.text.toString()
        tts!!.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
    }

    override fun onDestroy() {
        if(tts!=null){
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }
}