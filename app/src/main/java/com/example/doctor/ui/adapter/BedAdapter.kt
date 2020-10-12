package com.example.doctor.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.doctor.R
import com.example.doctor.data.model.BedModel
import kotlinx.android.synthetic.main.bed_item.view.*

class BedAdapter(val context: Context,val bedModels: ArrayList<BedModel> = ArrayList()) :
    RecyclerView.Adapter<BedAdapter.BedHolder>() {


    class BedHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BedHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.bed_item, parent, false)
        return BedHolder(view)
    }

    override fun getItemCount(): Int {
        return bedModels.size
    }

    override fun onBindViewHolder(holder: BedHolder, position: Int) {
        val bed = bedModels.get(position)
        holder.itemView.bed_num.text = bed.bedNum
        if (bed.bedStatus == 1) {
            holder.itemView.bed_status.text = "free"
            holder.itemView.bed_status.setTextColor(ContextCompat.getColor(context,R.color.green))
        }
        else {
            holder.itemView.bed_status.text = "busy"
            holder.itemView.bed_status.setTextColor(ContextCompat.getColor(context,R.color.red))
        }

    }
    fun clear() {
        bedModels.clear()
        notifyDataSetChanged()
    }

}