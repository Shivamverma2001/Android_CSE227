package com.example.firebase_2

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UpdateActivity : AppCompatActivity() {
    private lateinit var tvEmpId: TextView
    private lateinit var tvEmpName: EditText
    private lateinit var tvEmpAge: EditText
    private lateinit var tvEmpSalary: EditText
    lateinit var name:String
    lateinit var age:String
    lateinit var salary: String
    lateinit var id:String
    lateinit var update:Button
    private lateinit var dbRef:DatabaseReference
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
        tvEmpId=findViewById(R.id.tvEmpId)
        tvEmpName=findViewById(R.id.tvEmpName)
        tvEmpAge=findViewById(R.id.tvEmpAge)
        tvEmpSalary=findViewById(R.id.tvEmpSalary)
        update=findViewById(R.id.btnUpdate)
        setValuesToViews()

        update.setOnClickListener {
            name=tvEmpName.text.toString()
            age=tvEmpAge.text.toString()
            salary=tvEmpSalary.text.toString()
            updateEmpData(tvEmpId.text.toString(),name,age,salary)
        }

    }
    private fun updateEmpData(tvEmpId:String, name:String, age: String, salary:String){
        dbRef=FirebaseDatabase.getInstance().getReference("Employees")
        var empInfo=EmployeeModel(tvEmpId,name,age,salary)
        dbRef.child(tvEmpId).setValue(empInfo).addOnSuccessListener {
            Toast.makeText(this, "Update Successfully", Toast.LENGTH_SHORT).show()
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }.addOnFailureListener{error->
            Toast.makeText(this, "Deleting Error ${error.message}", Toast.LENGTH_SHORT).show()
        }
    }
    private fun setValuesToViews() {
        tvEmpId.setText(intent.getStringExtra("empId"))
        tvEmpName.setText(intent.getStringExtra("empName"))
        tvEmpAge.setText(intent.getStringExtra("empAge"))
        tvEmpSalary.setText(intent.getStringExtra("empSalary"))


    }
}