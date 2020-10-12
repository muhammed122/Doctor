package com.example.doctor.ui.location

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.example.doctor.data.helper.MyLocationProvider


open class BaseLocationFragment : Fragment(), LocationListener {

    val LOCATION_PERMISSION_REQUEST_CODE = 1;


    var locationProvider: MyLocationProvider? = null
    var myLocation: Location? = null


    val locationState=MutableLiveData<Boolean>(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        if (isLocationPermissionAllowed())
            statusCheck()
        else {
            //request permission
            requestLocationPermission();
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    fun isLocationPermissionAllowed(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
        ) ==
                PackageManager.PERMISSION_GRANTED
    }

    fun askToPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }

    fun getCurrentLocation() {

//        println(
//            "dola getCurrentLocation error : " + myLocation!!.latitude + "  " + myLocation!!.longitude
//        )
        if (locationProvider == null) {
            locationProvider = MyLocationProvider(requireContext())


            myLocation = locationProvider!!.getCurrentLocation(null)!!
        }
//            println(
//                "dola getCurrentLocation: " + myLocation!!.latitude + "  " + myLocation!!.longitude
//            )

    }

    fun statusCheck() {
        val manager =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            buildAlertMessageNoGps()
        else
            getCurrentLocation()

    }

    fun buildAlertMessageNoGps() {
        val builder =
            AlertDialog.Builder(requireContext())
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
            .setCancelable(false)
            .setPositiveButton(
                "Yes"
            ) { dialog, id -> startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) }
            .setNegativeButton(
                "No"
            ) { dialog, id -> dialog.cancel() }
        val alert = builder.create()
        alert.show()
    }


    fun requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            // In an educational UI, explain to the user why your app requires this
            // permission for a specific feature to behave as expected. In this UI,
            // include a "cancel" or "no thanks" button that allows the user to
            // continue using your app without granting the permission.
            showMessage()

        } else  // You can directly ask for the permission.
            askToPermission()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.size > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    // Permission is granted. Continue the action or workflow
                    // in your app.
                    getCurrentLocation()
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


    fun showMessage() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Location")
        builder.setMessage("We need your location ")

        builder.setPositiveButton("Okay") { dialog, which ->
            dialog.dismiss()
            askToPermission()
        }

        builder.setNegativeButton("Cancel") { dialog, which ->
            Toast.makeText(
                requireContext(),
                "permission denied", Toast.LENGTH_SHORT
            ).show()
            dialog.dismiss()
        }

        builder.show()
    }

    override fun onLocationChanged(location: Location?) {
        myLocation = location
        println("dola location $myLocation")
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        Toast.makeText(requireContext(), " $status", Toast.LENGTH_LONG).show()
    }

    override fun onProviderEnabled(provider: String?) {

            getCurrentLocation()


    }

    override fun onProviderDisabled(provider: String?) {

    }


}