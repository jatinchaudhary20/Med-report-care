package com.example.medreport_care.doctor.docAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
//import com.example.medreport_care.Doctor.Model.rvPateintModel
import com.example.medreport_care.doctor.model.rvPateintModel
import com.example.medreport_care.HealthWorker.Adapters.ViewHolder
import com.example.medreport_care.HealthWorker.Adapters.pateintListAdapter
import com.example.medreport_care.R

class docAdapter (private var docPateint : ArrayList<rvPateintModel> ): RecyclerView.Adapter<ViewHolder> (){

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    private lateinit var mListener : pateintListAdapter.onItemClickListener

    fun setOnItemClickListener(clickListener: pateintListAdapter.onItemClickListener){
        mListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.pateint_list_card,parent,false)
        return ViewHolder(itemView, mListener)
    }

    override fun getItemCount(): Int {
        return docPateint.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentPateint = docPateint[position]
        holder.pateintName.text= currentPateint.name.toString()
        holder.pateintnumber.text= currentPateint.bmi.toString()
        holder.pateintlocation.text= currentPateint.bp.toString()
    }

}


class ViewHolder(itemView: View, clickListener: pateintListAdapter.onItemClickListener) : RecyclerView.ViewHolder(itemView){
    var pateintName : TextView = itemView.findViewById(R.id.pdname)
    var pateintnumber : TextView =itemView.findViewById(R.id.phnumber)
    var pateintlocation : TextView =itemView.findViewById(R.id.pdlocation)

    init {
        itemView.setOnClickListener {
            clickListener.onItemClick(adapterPosition)
        }
    }


}