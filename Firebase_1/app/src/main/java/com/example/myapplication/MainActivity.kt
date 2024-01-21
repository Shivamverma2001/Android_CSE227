package com.example.myapplication

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    lateinit var a:FirebaseAuth
    lateinit var submit:Button
    lateinit var email:EditText
    lateinit var pass:EditText
    lateinit var login:Button
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        submit=findViewById(R.id.btn)
        email=findViewById(R.id.editText)
        pass=findViewById(R.id.editText2)
        login=findViewById(R.id.Login)
        a=FirebaseAuth.getInstance()

        submit.setOnClickListener {
            a.createUserWithEmailAndPassword(email.text.toString(),pass.text.toString()).addOnCompleteListener {

                a.currentUser?.sendEmailVerification()?.addOnCompleteListener {
                    if(it.isSuccessful){
                        Toast.makeText(this,"Email Sent Successfully",Toast.LENGTH_LONG).show()
                    }
                    else{
                        Toast.makeText(this,"Email not sent Successfully"+it.exception,Toast.LENGTH_LONG).show()
                    }
                }

                if(it.isSuccessful){
                    Toast.makeText(this,"Sign Up successfully",Toast.LENGTH_LONG).show()
                }
                else{
                    Toast.makeText(this,"Please enter right email or password"+it.exception,Toast.LENGTH_LONG).show()
                }
            }
        }

        login.setOnClickListener {
            a.signInWithEmailAndPassword(email.text.toString(),pass.text.toString()).addOnCompleteListener {
                if(it.isSuccessful){
                    Toast.makeText(this,"Login successfully",Toast.LENGTH_LONG).show()
                }
                else{
                    Toast.makeText(this,"Wrong email and password"+it.exception,Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}