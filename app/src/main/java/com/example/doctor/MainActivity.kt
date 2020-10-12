package com.example.doctor

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager


class MainActivity : AppCompatActivity() {

//    val LOCATION_PERMISSION_REQUEST_CODE = 1;
//
//
//    var locationProvider: MyLocationProvider? = null
//    var myLocation: Location? = null
//
//
//    private val sharedPrefFile = "locationfile"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val memes =
            PreferenceManager.getDefaultSharedPreferences(this).edit()
        memes.putStringSet("PREF_COOKIES" ,null).apply()
        memes.commit()

        println("dola shared deleted")

    }


//    fun isLocationPermissionAllowed(): Boolean {
//        return ContextCompat.checkSelfPermission(
//            this, Manifest.permission.ACCESS_FINE_LOCATION
//        ) ==
//                PackageManager.PERMISSION_GRANTED
//    }
//
//    fun askToPermission() {
//        ActivityCompat.requestPermissions(
//            this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
//            LOCATION_PERMISSION_REQUEST_CODE
//        )
//    }/
//
//    fun getCurrentLocation() {
//
//        if (locationProvider == null)
//            locationProvider = MyLocationProvider(this)
//
//
//        myLocation = locationProvider!!.getCurrentLocation(this)!!
//        println(
//            "dola getCurrentLocation: " + myLocation!!.latitude + "  " + myLocation!!.longitude
//        )
//    }
//
//    fun requestLocationPermission() {
//        if (ActivityCompat.shouldShowRequestPermissionRationale(
//             this,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            )
//        ) {
//            // In an educational UI, explain to the user why your app requires this
//            // permission for a specific feature to behave as expected. In this UI,
//            // include a "cancel" or "no thanks" button that allows the user to
//            // continue using your app without granting the permission.
//            showMessage()
//
//        } else  // You can directly ask for the permission.
//            askToPermission()
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        when (requestCode) {
//            LOCATION_PERMISSION_REQUEST_CODE -> {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.size > 0 &&
//                    grantResults[0] == PackageManager.PERMISSION_GRANTED
//                ) {
//                    // Permission is granted. Continue the action or workflow
//                    // in your app.
//                    getCurrentLocation()
//                } else {
//                    // Explain to the user that the feature is unavailable because
//                    // the features requires a permission that the user has denied.
//                    // At the same time, respect the user's decision. Don't link to
//                    // system settings in an effort to convince the user to change
//                    // their decision.
//                    Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show()
//                }
//                return
//            }
//        }
//
//    }
//
//
//    fun showMessage() {
//        val builder = AlertDialog.Builder(this)
//        builder.setTitle("Location")
//        builder.setMessage("We need your location ")
//
//        builder.setPositiveButton("Okay") { dialog, which ->
//            dialog.dismiss()
//            askToPermission()
//        }
//
//        builder.setNegativeButton("Cancel") { dialog, which ->
//            Toast.makeText(
//                this,
//                "permission denied", Toast.LENGTH_SHORT
//            ).show()
//            dialog.dismiss()
//        }
//
//        builder.show()
//    }
//
//    override fun onLocationChanged(location: Location?) {
//        myLocation = location
//    }
//
//    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
//        TODO("Not yet implemented")
//    }
//
//    override fun onProviderEnabled(provider: String?) {
//        TODO("Not yet implemented")
//    }
//
//    override fun onProviderDisabled(provider: String?) {
//        TODO("Not yet implemented")
//    }
//
//
//    fun saveLocationInShared(){
//
//
//    }


}