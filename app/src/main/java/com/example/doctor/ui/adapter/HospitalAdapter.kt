package com.example.doctor.ui.adapter

import android.content.Context
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.doctor.R
import com.example.doctor.data.helper.FirebaseHelper
import com.example.doctor.data.model.HospitalModel
import kotlinx.android.synthetic.main.hospital_item.view.*
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

class HospitalAdapter(
    val context: Context,
    private val hospitals: ArrayList<HospitalModel> = ArrayList(),
    private val location: Location?
) :
    RecyclerView.Adapter<HospitalAdapter.HospitalHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HospitalHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.hospital_item, parent, false)

        return HospitalHolder(
            itemView
        )
    }

    override fun getItemCount(): Int {
        return hospitals.size
    }


    private fun distance(
        lat1: Double,
        lon1: Double,
        lat2: Double,
        lon2: Double,
        unit: String
    ): Double {
        return if (lat1 == lat2 && lon1 == lon2) {
            0.0
        } else {
            val theta = lon1 - lon2
            var dist =
                sin(Math.toRadians(lat1)) * sin(
                    Math.toRadians(lat2)
                ) + cos(Math.toRadians(lat1)) * cos(
                    Math.toRadians(
                        lat2
                    )
                ) * cos(Math.toRadians(theta))
            dist = acos(dist)

            dist = Math.toDegrees(dist)
            dist *= 60 * 1.1515


            dist *= 1.609344

            dist
        }
    }


    override fun onBindViewHolder(holder: HospitalHolder, position: Int) {
        val hospital = hospitals.get(position)

        holder.itemView.hospital_name.text = hospital.name
        holder.itemView.hospital_location.text = hospital.location
        holder.itemView.available_beds.text = hospital.availBeds.toString()
        holder.itemView.busy_beds.text = hospital.busyBeds.toString()
        holder.itemView.tvIcon.text = hospital.name.subSequence(0, 1)

        if (location != null) {


            //    println("dola lat ${hospital.latitude}   lng ${hospital.langitude} ")
            val dist = distance(
                location.latitude, location.longitude
                , hospital.langitude, hospital.latitude, "k"
            )

            var time = (dist / 50)
            time *= 60
//            val dist=distance(29.9285, 30.9188, 30.088180105663767,31.217128533919183,"k")
            holder.itemView.tvDistance.text = "${dist.roundToInt()} km"
            holder.itemView.tvTime.text = "${time.roundToInt()}  min"


        }

//       holder.itemView.setOnClickListener {
//            val data = Bundle()
//            //   data.putString("movie_id", movie?.id.toString())
//            data.putInt("movie_id",movie?.id as Int)
//            itemView.findNavController().navigate(
//                R.id.action_popularMovies_to_movieDetails,
//                data
//            )
//        }


        holder.itemView.show_location.setOnClickListener {
            val data = Bundle()
            val dest = "${hospital.langitude} , ${hospital.latitude}"
            data.putString("dest", dest)
            data.putDouble("lat",hospital.langitude)
            data.putDouble("lng",hospital.latitude)
            it.findNavController().navigate(
                R.id.hospitalsLocations,
                data
            )
        }


//        holder.itemView.setOnClickListener { itemview ->
//            itemview.findNavController().navigate(
//                R.id.action_hospital_to_statusBed
//            )
//        }








        holder.itemView.book_btn.setOnClickListener {

            if (hospital.availBeds == 0)
                Toast.makeText(context, "Sorry No available beds ", Toast.LENGTH_LONG).show()
            else {
                val builder = AlertDialog.Builder(context)
                builder.setTitle("Booking")
                builder.setMessage("Confirm Booking..?")

                builder.setPositiveButton("Confirm") { dialog, which ->

                    it.book_btn.text = "Booked"
                    it.book_btn.background =
                        ContextCompat.getDrawable(context, R.drawable.booked_btn)

                    val bed=hospital.availBeds-1;
                    FirebaseHelper.firebaseReference.child(hospital.name)
                        .child("emptyofunits").setValue(bed)

                    Toast.makeText(
                        context,
                        "confirm", Toast.LENGTH_SHORT
                    ).show()


                }

                builder.setNegativeButton("Cancel") { dialog, which ->
                    Toast.makeText(
                        context,
                        "Cancel", Toast.LENGTH_SHORT
                    ).show()
                }

                builder.show()
            }
        }
    }

    fun clear() {
        hospitals.clear()
        notifyDataSetChanged()
    }

    class HospitalHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        override fun onClick(v: View?) {
        }
    }






    interface OnHospitalListener {
        fun onHospitalClick(position: Int)
    }
}