package com.janayalsalem.mapapp.core.common

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.location.*

/*
- BaseFragment : is for using current location
- We can use it for any app need the current location
 */

open class BaseFragment : Fragment() {
    private var locationCallback: LocationCallback? = null
    private var locationRequest : LocationRequest?= null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupLocationClient()
    }

    private fun setupLocationClient() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getRootActivity())
    }

    fun getRootActivity() = activity as FragmentActivity

    @SuppressLint("MissingPermission")
    fun getLastKnownLocation(onlocationAvailable: (Location) -> Unit) {
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null)
                onlocationAvailable(location)
            else
                createLocationRequest(onlocationAvailable)
        }
    }

    // Location Request
    private fun createLocationRequest(onlocationAvailable: (Location) -> Unit) {
        locationRequest = LocationRequest.create().apply {
            interval = 5000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                for(location in result.locations){
                    onlocationAvailable(location)
                }
            }
        }
        requestLocationUpdates()
    }

    // Location Request if state is destroy
    @SuppressLint("MissingPermission")
    private fun requestLocationUpdates(){
        if(locationCallback !=null && locationRequest !=null)
            fusedLocationClient.requestLocationUpdates(locationRequest,locationCallback, Looper.myLooper())
    }

    private fun stopLocationUpdates(){
        if (fusedLocationClient!=null && locationCallback !=null)
            fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    override fun onResume() {
        super.onResume()
        requestLocationUpdates()
    }

    override fun onStop() {
        super.onStop()
        stopLocationUpdates()
    }

    // is GPS Enabled ?
    fun isLocationEnabled() : Boolean{
        val locationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return  locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

}