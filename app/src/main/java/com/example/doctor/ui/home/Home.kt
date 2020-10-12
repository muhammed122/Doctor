package com.example.doctor.ui.home

import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.doctor.R
import com.example.doctor.ui.location.BaseLocationFragment
import kotlinx.android.synthetic.main.fragment_home.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Home.newInstance] factory method to
 * create an instance of this fragment.
 */
class Home : BaseLocationFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navigate_chatbot.setOnClickListener {
            findNavController().navigate(R.id.chatRoom)
        }


        navigateStatusBed.setOnClickListener {
            findNavController().navigate(
                R.id.statusBed
            )
        }


        navigate_hospitals.setOnClickListener {

            if (myLocation == null)
                detailsOfLocation()
            else
                findNavController().navigate(R.id.hospital)


        }


    }


    fun statusLocationCheck(): Boolean {
        val manager =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            buildAlertMessageNoGps()
        else
            getCurrentLocation()

        if (myLocation == null)
            return false

        return true
    }


    fun detailsOfLocation() {
        val builder =
            AlertDialog.Builder(requireContext())
        builder.setMessage(
            "sorry you can not use this service without open GPS" +
                    ", do you want to enable it?"
        )
            .setCancelable(false)
            .setPositiveButton(
                "Yes"
            ) { dialog,
                id ->
                startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                if (statusLocationCheck())
                    findNavController().navigate(R.id.hospital)
                else
                    Toast.makeText(requireContext(), "Sorry try later..", Toast.LENGTH_LONG).show()
            }
            .setNegativeButton(
                "No"
            ) { dialog, _ -> dialog.cancel() }
        val alert = builder.create()
        alert.show()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val imm: InputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

}