package com.example.medreport_care.HealthWorker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.medreport_care.HealthWorker.Adapters.pateintListAdapter
import com.example.medreport_care.HealthWorker.Model.PateintModel
import com.example.medreport_care.HealthWorker.Model.rvlistModel
import com.example.medreport_care.R
import com.example.medreport_care.databinding.ActivityPatientListBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class patientList : AppCompatActivity() {


    private lateinit var binding : ActivityPatientListBinding
    private lateinit var hwref : DatabaseReference
    private lateinit var Pateintlist :ArrayList<rvlistModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityPatientListBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //recycler view
        val rvhwl = binding.rvHwl
        rvhwl.layoutManager= LinearLayoutManager(this)
        rvhwl.setHasFixedSize(true)

        Pateintlist= arrayListOf<rvlistModel>()

        hwref= FirebaseDatabase.getInstance().getReference("Health Worker")
        pateintlistfetch()



    }


    private fun pateintlistfetch(){
        binding.rvHwl.visibility = View.GONE
        binding.logout.visibility=View.GONE
        binding.menuloadrv.visibility = View.VISIBLE



        hwref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Pateintlist.clear()
                if (snapshot.exists()) {

                    for (pateintsnap in snapshot.children) {
                        val pateintData = pateintsnap.getValue(rvlistModel::class.java)
                        Pateintlist.add(pateintData!!)
                    }

                    val menuAdapter = pateintListAdapter(Pateintlist)
                    binding.rvHwl.adapter=menuAdapter


                    menuAdapter.setOnItemClickListener(object: pateintListAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            //need to change here acc to user activity
                            val intent = Intent(this@patientList,landingHealthWorker::class.java)
                            intent.putExtra("userId",Pateintlist[position].userId)
                            startActivity(intent)
                        }
                    })
                    binding.rvHwl.visibility = View.VISIBLE
                    binding.logout.visibility=View.VISIBLE
                    binding.menuloadrv.visibility = View.GONE

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }
}