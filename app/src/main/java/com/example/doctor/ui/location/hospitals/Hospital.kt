package com.example.doctor.ui.location.hospitals

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.doctor.R
import com.example.doctor.data.model.HospitalModel
import com.example.doctor.ui.adapter.HospitalAdapter
import com.example.doctor.ui.location.BaseLocationFragment
import kotlinx.android.synthetic.main.fragment_hosbital.*
import kotlinx.android.synthetic.main.fragment_hospitals_locations.*
import java.util.*



class Hospital : BaseLocationFragment() {


    lateinit var hospitalAdapter: HospitalAdapter
    lateinit var layoutManager: RecyclerView.LayoutManager
    val CALL_PHONE_PERMESSION_CODE = 2




    lateinit var hospitalViewModel: HospitalViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hosbital, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipe_hospitals.setOnRefreshListener {

            hospitalAdapter.clear()
            hospitalViewModel.getHospitals()

        }


        hospital_progress.visibility = View.VISIBLE

        hospitalViewModel =
            ViewModelProviders.of(requireActivity()).get(HospitalViewModel::class.java)
        hospitalViewModel.getHospitals()
        hospitalViewModel.hospitals.observe(viewLifecycleOwner, Observer { data ->


            setupRecycler(data as ArrayList<HospitalModel>)

            hospitalAdapter.notifyDataSetChanged()


        })

        hospitalViewModel.message.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        })


        call_ambulance.setOnClickListener {

            if (isCallPermissionAllowed()) {
                callAmbulance()
            } else {
                requestCallPermission()
            }

        }

    }
    fun callAmbulance() {
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:123"))
        startActivity(intent)
    }

    fun isCallPermissionAllowed(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.CALL_PHONE
        ) ==
                PackageManager.PERMISSION_GRANTED
    }


    fun requestCallPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.CALL_PHONE
            )
        ) {
            // In an educational UI, explain to the user why your app requires this
            // permission for a specific feature to behave as expected. In this UI,
            // include a "cancel" or "no thanks" button that allows the user to
            // continue using your app without granting the permission.
            showCallMessage()

        } else  // You can directly ask for the permission.
            askToCallPermission()
    }

    fun askToCallPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(), arrayOf(Manifest.permission.CALL_PHONE),
            CALL_PHONE_PERMESSION_CODE
        )
    }

    override fun onLocationChanged(location: Location?) {
        super.onLocationChanged(location)
        hospitalAdapter.notifyDataSetChanged()
    }



    fun showCallMessage() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Call")
        builder.setMessage("We need your call permission ")

        builder.setPositiveButton("Okay") { dialog, which ->
            askToCallPermission()
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancel") { dialog, which ->
            Toast.makeText(
                requireContext(),
                "Permission denied", Toast.LENGTH_SHORT
            ).show()
            dialog.dismiss()
        }

        builder.show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CALL_PHONE_PERMESSION_CODE -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.size > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    // Permission is granted. Continue the action or workflow
                    // in your app.
                    callAmbulance()
                } else {
                    // Explain to the user that the feature is unavailable because
                    // the features requires a permission that the user has denied.
                    // At the same time, respect the user's decision. Don't link to
                    // system settings in an effort to convince the user to change
                    // their decision.
                    Toast.makeText(requireContext(), "permission denied", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }

    }


    fun setupRecycler(hospitals: ArrayList<HospitalModel>) {
//      addFakeData()
        layoutManager = LinearLayoutManager(context)
        hospitalAdapter = HospitalAdapter(requireContext(), hospitals,myLocation)
        hospital_list.adapter = hospitalAdapter
        hospital_list.layoutManager = layoutManager

        hospital_progress.visibility = View.GONE
        swipe_hospitals.isRefreshing=false


    }

//    fun addFakeData(){
//        hospitals= ArrayList()
//        for(i in 0..10) {
//            hospitals.add(HospitalModel("1", "Hospital Name",
//                3,3,"herer"))
//
//        }

}

