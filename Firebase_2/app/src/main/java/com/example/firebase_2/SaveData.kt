package com.example.firebase_2

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class SaveData : AppCompatActivity() {
    lateinit var name:EditText
    lateinit var age:EditText
    lateinit var salary:EditText
    lateinit var save:Button
    lateinit var dbRef: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save_data)

        name=findViewById(R.id.editTextText)
        age=findViewById(R.id.editTextText2)
        salary=findViewById(R.id.editTextText3)
        save=findViewById(R.id.button3)

        dbRef = FirebaseDatabase.getInstance().getReference("Employees")

        save.setOnClickListener {
            saveEmployeeData()
        }
    }

    private fun saveEmployeeData() {

//getting values
        val empName = name.text.toString()
        val empAge = age.text.toString()
        val empSalary = salary.text.toString()

        if (empName.isEmpty()) {
            name.error = "Please enter name"
        }
        if (empAge.isEmpty()) {
            age.error = "Please enter age"
        }
        if (empSalary.isEmpty()) {
            salary.error = "Please enter salary"
        }

        val empId = dbRef.push().key!!

        val employee = EmployeeModel(empId, empName, empAge, empSalary)

        dbRef.child(empId).setValue(employee)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                name.text.clear()
                age.text.clear()
                salary.text.clear()


            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }

    }

}