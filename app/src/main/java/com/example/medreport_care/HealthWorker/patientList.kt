package com.example.medreport_care.HealthWorker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.medreport_care.HealthWorker.Model.PateintModel
import com.example.medreport_care.R
import com.example.medreport_care.databinding.ActivityPatientListBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class patientList : AppCompatActivity() {
    private lateinit var binding : ActivityPatientListBinding
    private lateinit var hwref : DatabaseReference
    private lateinit var Pateintlist :ArrayList<PateintModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityPatientListBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //recycler view
        val rvhwl = binding.rvHwl
        rvhwl.layoutManager= LinearLayoutManager(this)
        rvhwl.setHasFixedSize(true)

        Pateintlist= arrayListOf<PateintModel>()

        hwref= FirebaseDatabase.getInstance().getReference("Health Worker")




    }


    private fun pateintlistfetch(){
        binding.rvHwl.visibility = View.GONE
        binding.logout.visibility=View.GONE
        binding.menuloadrv.visibility = View.VISIBLE



    }
}