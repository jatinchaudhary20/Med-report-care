package com.example.medreport_care.HealthWorker.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.medreport_care.HealthWorker.Model.PateintModel
import com.example.medreport_care.R

    class pateintListAdapter(private var pateintlist :ArrayList<PateintModel>)  : RecyclerView.Adapter<ViewHolder> () {


        interface onItemClickListener{
            fun onItemClick(position: Int)
        }

        private lateinit var mListener : onItemClickListener

        fun setOnItemClickListener(clickListener: onItemClickListener){
            mListener = clickListener
        }



        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.pateint_list_card,parent,false)
            return ViewHolder(itemView, mListener)
        }

        override fun getItemCount(): Int {
            return pateintlist.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            TODO("Not yet implemented")
        }


    }

    class ViewHolder(itemView: View, clickListener: pateintListAdapter.onItemClickListener) : RecyclerView.ViewHolder(itemView){
        var pateintName : TextView = itemView.findViewById(R.id.pdname)
        var pateintnumber :TextView =itemView.findViewById(R.id.phnumber)
        var pateintlocation :TextView =itemView.findViewById(R.id.pdlocation)

        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }


    }

