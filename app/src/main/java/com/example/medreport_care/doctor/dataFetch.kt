package com.example.medreport_care.doctor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.medreport_care.R
import com.example.medreport_care.databinding.ActivityDataFetchBinding

class dataFetch : AppCompatActivity() {
    private lateinit var binding :ActivityDataFetchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityDataFetchBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
        setValuesToViews()




    }

    private fun initView(){
        binding.pdbmi
        binding.pdname
        binding.pddoctorname
        binding.pdsugar
        binding.pdbp
        binding.pdheartrate
    }

    private fun setValuesToViews(){
        // its a wrong approach i think
        // i have to fetch the data from the user id
        binding.pdname.text= intent.getStringExtra("name")
        binding.pdbmi.text= intent.getStringExtra("bmi")
        binding.pdbp.text= intent.getStringExtra("bp")
        binding.pdheartrate.text= intent.getStringExtra("heartrate")
        binding.pdsugar.text=intent.getStringExtra("sugar")
        binding.pddoctorname.text= intent.getStringExtra("doctorname")

    }
}