package com.example.medreport_care.doctor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.medreport_care.doctor.model.rvPateintModel
import com.example.medreport_care.doctor.docAdapter.docAdapter
import com.example.medreport_care.HealthWorker.Adapters.pateintListAdapter
import com.example.medreport_care.MainActivity
import com.example.medreport_care.databinding.ActivityReportListBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class reportList : AppCompatActivity() {

    private lateinit var binding : ActivityReportListBinding
    private lateinit var docref : DatabaseReference
    private lateinit var docdbref : DatabaseReference
    private lateinit var Pateintlist :ArrayList<rvPateintModel>


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityReportListBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val rvhwl = binding.rvHwl
        rvhwl.layoutManager= LinearLayoutManager(this)
        rvhwl.setHasFixedSize(true)
        Pateintlist= arrayListOf<rvPateintModel>()

        getPateintDataFromDoctorProfile();

    }

    private fun getPateintDataFromDoctorProfile(){
        binding.rvHwl.visibility = View.GONE
        binding.logout.visibility=View.GONE
        binding.menuloadrv.visibility = View.VISIBLE

        val docId = FirebaseAuth.getInstance().currentUser?.uid.toString()
        docref= FirebaseDatabase.getInstance().getReference("Doctor Data").child(docId)

        docref.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                if (dataSnapshot.exists()) {
                    val doctorName = dataSnapshot.getValue(String::class.java).toString()


                    docdbref = FirebaseDatabase.getInstance().getReference("Doctor Data").child(doctorName)


                    docdbref.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            Pateintlist.clear()
                            if (snapshot.exists()) {
                                for (pateintsnap in snapshot.children) {
                                    val pateintData = pateintsnap.getValue(rvPateintModel::class.java)
                                    Pateintlist.add(pateintData!!)

//                                    val data = snapshot.value as Map<String, Any>

                                }

                                val menuAdapter = docAdapter(Pateintlist)
                                binding.rvHwl.adapter=menuAdapter


                                menuAdapter.setOnItemClickListener(object: pateintListAdapter.onItemClickListener{
                                    override fun onItemClick(position: Int) {
                                        //need to change here acc to user activity
                                        val intent = Intent(this@reportList,MainActivity::class.java)
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