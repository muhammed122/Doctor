package com.example.doctor.data.helper

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager

class MyLocationProvider(context: Context) {

    private val DESTANCE_BETWEEN_UPDATE = 5 * 1000.toFloat()
    private val TIME_BETWEEN_UPDATE = 5 * 1000.toLong()


    private var locationManager: LocationManager =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    private var location: Location? = null

    private fun providerEnabled(): Boolean {
        val gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val netWork =
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        return gps || netWork
    }

    @SuppressLint("MissingPermission")
    fun getCurrentLocation(locationListener: LocationListener?): Location? {
        if (!providerEnabled())
            return null

        var provider = LocationManager.GPS_PROVIDER
        if (!locationManager.isProviderEnabled(provider))
            provider = LocationManager.NETWORK_PROVIDER

        location = locationManager.getLastKnownLocation(provider)

        if (location == null) {
            location = getBestLocation()
        }
        if (locationListener != null) {
            locationManager.requestLocationUpdates(
                provider, TIME_BETWEEN_UPDATE, DESTANCE_BETWEEN_UPDATE,
                locationListener
            )
        }

        return location

    }

    private fun getBestLocation(): Location? {
        val providers = locationManager.allProviders
        var bestLocation: Location? = null
        for (provider in providers) {
            @SuppressLint("MissingPermission")
            val temp =
                locationManager.getLastKnownLocation(provider)
                    ?: continue
            if (bestLocation == null) bestLocation =
                temp else if (temp.accuracy > bestLocation.accuracy) bestLocation = temp
        }
        return bestLocation
    }


}