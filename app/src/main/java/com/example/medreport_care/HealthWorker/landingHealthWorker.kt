package com.example.medreport_care.HealthWorker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.medreport_care.HealthWorker.Model.PateintModel
import com.example.medreport_care.R
import com.example.medreport_care.databinding.ActivityLandingHealthWorkerBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class landingHealthWorker : AppCompatActivity() {
    private lateinit var binding : ActivityLandingHealthWorkerBinding

    private lateinit var pateintref: DatabaseReference
    private lateinit var docref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandingHealthWorkerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pateintref = FirebaseDatabase.getInstance().getReference("Pateint Report")
        docref = FirebaseDatabase.getInstance().getReference("Doctor Report")

        binding.submitbutton.setOnClickListener {

            saveEmployeeData()
            clearBlocks()

        }

    }

    private fun checkAllfield(): Boolean {
        if (binding.reportName.text.toString() == "") {
            binding.reportName.error = "Field required"
            return false
        }
        if (binding.reportBmi.text.toString() == "") {
            binding.reportBmi.error = "Field required"
            return false
        }
        if (binding.reportBp.text.toString() == "") {
            binding.reportBp.error = "Field required"
            return false
        }
        if (binding.reportHeartRate.text.toString() == "") {
            binding.reportHeartRate.error = "Field required"
            return false
        }

        if (binding.reportSugarLevel.text.toString() == "") {
            binding.reportSugarLevel.error = "Field required"
            return false
        }
        if (binding.docname.text.toString() == "") {
            binding.docname.error = "Field required"
            return false
        }


        return true
    }

    private fun clearBlocks() {
        binding.reportBp.text?.clear()
        binding.reportBmi.text?.clear()
        binding.reportName.text?.clear()
        binding.reportHeartRate.text?.clear()
        binding.reportSugarLevel.text?.clear()
        binding.docname.text?.clear()
    }

    private fun saveEmployeeData() {
        val name = binding.reportName.text.toString()
        val bmi = binding.reportBmi.text.toString()
        val bp = binding.reportBp.text.toString()
        val heartrate = binding.reportHeartRate.text.toString()
        val sugarlevel = binding.reportSugarLevel.text.toString()
        val doctorname = binding.docname.text.toString()

        if (checkAllfield()) {
            //patent user id getting from Patent list activity
            val currentUser = intent.getStringExtra("userId")


            if (currentUser != null) {
                //getting its uid
                val userId = currentUser



                docref = FirebaseDatabase.getInstance().getReference("Doctor Data").child(doctorname).child(userId)

                // Create a patient object
                val patient = PateintModel(userId, name, bmi, bp, heartrate,sugarlevel, doctorname)

                // Set the patient data under the user's reference
                docref.setValue(patient).addOnCompleteListener {
                    Toast.makeText(this, "Data Inserted Success", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(this, "Fail To Insert Data", Toast.LENGTH_SHORT).show()
                }

                pateintref.child(userId).setValue(patient)
            }
            else {
                // Handle the case where the current user is null
                Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
            }


        }
    }


}