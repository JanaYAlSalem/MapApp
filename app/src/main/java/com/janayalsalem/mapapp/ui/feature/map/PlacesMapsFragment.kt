package com.janayalsalem.mapapp.ui.feature.map


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.janayalsalem.mapapp.R
import com.janayalsalem.mapapp.core.common.BaseFragment
import permissions.dispatcher.*
import timber.log.Timber

/*
- To App Know in this Fragment specifically we want permission dispatcher will we use [@RuntimePermissions]
- We need permission to current location
 */
@RuntimePermissions
class PlacesMapsFragment : BaseFragment() {
    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        val sydney = LatLng(-34.0, 151.0)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_places_maps, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

//        getCurrentLocationWithPermissionCheck()

    }
    /*
    - ** Handle Permissions **
    - 1 -> NeedsPermission :
    - 2 -> OnShowRationale
    - 3-> OnPermissionDenied
    - 4 -> OnNeverAskAgain
    */

    // region 1- NeedsPermission
    /*
    - call isLocationEnabled() from base to know the state of GPS
     ture -> we use a getLastKnownLocation which is need a lambda fun
     false -> we show a Dialog FragmentActivity
     */
    @NeedsPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
    fun getCurrentLocation() {
        if (isLocationEnabled()) {
            getLastKnownLocation {
                // log the location
                Timber.e("available lat , long: %s,%s", it.latitude, it.longitude)
//                //call foursquare api to get restaurants
//                val currentLatLng = LatLng(it.latitude ,it.longitude)
//                val currentBounds = googleMap?.projection?.visibleRegion?.latLngBounds
//                if (currentBounds!=null && currentLatLng!=null)
//                    mapViewModel.getRestaurants(RequestDto(currentLatLng,currentBounds))

            }
        } else {
            MaterialAlertDialogBuilder(getRootActivity())
                .setTitle(getString(R.string.location_not_enabled))
                .setMessage(getString(R.string.enable_location))
                .setPositiveButton(getString(R.string.enable)) { dialog, _ ->
                    // open settings screen
                    openSettingsScreen()
                    dialog.dismiss()
                }
                .setNegativeButton(getString(R.string.deny)) { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }

    } // end fun getCurrentLocation
    private fun openSettingsScreen() {
    }
    //endregion

    // region 2- OnShowRationale
    @OnShowRationale(android.Manifest.permission.ACCESS_FINE_LOCATION)
    fun OnRationalAskLocation(request: PermissionRequest) {
    }
    //endregion

    // region 3- OnPermissionDenied
    @OnPermissionDenied(android.Manifest.permission.ACCESS_FINE_LOCATION)
    fun OnDenyAskLocation() {
    }
    //endregion

    // region 4- OnNeverAskAgain
    @OnNeverAskAgain(android.Manifest.permission.ACCESS_FINE_LOCATION)
    fun OnNeverAskLocation() {
//        val openApplicationSettings = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
//        openApplicationSettings.data = Uri.fromParts("package", activity?.packageName, null)
//        applicationSettingsScreen.launch(openApplicationSettings)
    }
    //endregion

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<out String>,
                                            grantResults: IntArray) {
//        this.onRequestPermissionsResult(requestCode, grantResults)
    }
}