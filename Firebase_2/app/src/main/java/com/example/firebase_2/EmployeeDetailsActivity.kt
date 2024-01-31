package com.example.firebase_2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class EmployeeDetailsActivity : AppCompatActivity() {
    private lateinit var tvEmpId:TextView
    private lateinit var tvEmpName:TextView
    private lateinit var tvEmpAge:TextView
    private lateinit var tvEmpSalary:TextView
    private lateinit var update:Button
    private lateinit var delete:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_details)

        tvEmpId=findViewById(R.id.tvEmpId)
        tvEmpName=findViewById(R.id.tvEmpName)
        tvEmpAge=findViewById(R.id.tvEmpAge)
        tvEmpSalary=findViewById(R.id.tvEmpSalary)
        update=findViewById(R.id.btnUpdate)
        delete=findViewById(R.id.btnDelete)

        setValuesToViews()

        update.setOnClickListener {
            val i=Intent(this,UpdateActivity::class.java)
            i.putExtra("empId",tvEmpId.text.toString())
            i.putExtra("empName",tvEmpName.text.toString())
            i.putExtra("empAge",tvEmpAge.text.toString())
            i.putExtra("empSalary",tvEmpSalary.text.toString())

            startActivity(i)
        }
        delete.setOnClickListener {
            deleteRecord(tvEmpId.text.toString())
        }
    }
    private fun setValuesToViews() {
        tvEmpId.text = intent.getStringExtra("empId")
        tvEmpName.text = intent.getStringExtra("empName")
        tvEmpAge.text = intent.getStringExtra("empAge")
        tvEmpSalary.text = intent.getStringExtra("empSalary")

    }
    private fun deleteRecord(id:String){
        val dbRef=FirebaseDatabase.getInstance()
            .getReference("Employees").child(id)
        val mTask=dbRef.removeValue()
        mTask.addOnSuccessListener {
            Toast.makeText(this, "Deletion Successfull", Toast.LENGTH_SHORT).show()
            val i=Intent(this,ShowDetails::class.java)
            startActivity(i)
        }.addOnFailureListener { error->
            Toast.makeText(this, "Not successfull "+error.message, Toast.LENGTH_SHORT).show()
        }
    }
}