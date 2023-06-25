package com.sakr.prayertimesapp.app.ui.direction

import android.Manifest
import android.annotation.SuppressLint
import android.content.IntentSender
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.gms.tasks.Task
import com.sakr.prayertimesapp.R
import com.sakr.prayertimesapp.data.local.PreferencesHelper
import com.sakr.prayertimesapp.utils.toBitmap
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DirectionFragment : Fragment() {

    private lateinit var mGoogleMap: com.google.android.gms.maps.GoogleMap
    val REQUEST_CHECK_SETTINGS = 100
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val kaaba = LatLng(21.422487, 39.826206)

    @Inject
    lateinit var prefrencesHelper: PreferencesHelper

    private val callback = OnMapReadyCallback { googleMap ->
        mGoogleMap = googleMap
        googleMap.addMarker(
            MarkerOptions()
                .position(kaaba)
                .title("الكعبة المشرفة") // below line is use to add
                .icon(
                    BitmapDescriptorFactory.fromBitmap(
                        R.drawable.kaaba.toBitmap(requireContext())!!
                    )
                )
        )
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(30.9790438, 31.1675868)))

        googleMap.setOnMyLocationButtonClickListener {
            enableMyLocation()
            true
        }
        googleMap.setOnMyLocationClickListener {
        }
        enableMyLocation()


    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(
            R.layout.fragment_direction,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }


    private fun enableMyLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            return
        } else {
            mGoogleMap.isMyLocationEnabled = true
            checkDeviceLocationSettings()
        }
    }

    @SuppressLint("MissingPermission")
    private fun checkDeviceLocationSettings() {
        val locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        val client: SettingsClient = LocationServices.getSettingsClient(requireActivity())
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())
        task.addOnSuccessListener { locationSettingsResponse ->
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: android.location.Location? ->
                    if (location != null) {
                        val currentLatLng = LatLng(location.latitude, location.longitude)
                        mGoogleMap.animateCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                currentLatLng,
                                5f
                            )
                        )
                        mGoogleMap.animateCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                currentLatLng,
                                5f
                            )
                        )
                        mGoogleMap.addMarker(
                            MarkerOptions()
                                .position(currentLatLng)
                                .title("موقعك الحالي")

                        )

                        val overlayOptions = GroundOverlayOptions()
                            .image(
                                BitmapDescriptorFactory.fromBitmap(
                                    R.drawable.right_arrow.toBitmap(
                                        requireContext()
                                    )!!
                                )
                            )
                            .positionFromBounds(
                                LatLngBounds.Builder().apply {
                                    include(kaaba)
                                    include(LatLng(30.9790438, 31.1675868))
                                }.build()
                            )
                            .bearing(
                                prefrencesHelper.getQiblaFromLocal()?.toFloat() ?: 135f
                            )
                            .visible(true)
                        mGoogleMap.addGroundOverlay(overlayOptions)

                    } else {
                        fusedLocationClient.getCurrentLocation(
                            LocationRequest.PRIORITY_HIGH_ACCURACY,
                            null
                        )
                            .addOnSuccessListener { currentLocation: android.location.Location? ->
                                if (currentLocation != null) {
                                    val currentLatLng =
                                        LatLng(currentLocation.latitude, currentLocation.longitude)
                                    mGoogleMap.animateCamera(
                                        CameraUpdateFactory.newLatLngZoom(
                                            currentLatLng,
                                            5f
                                        )
                                    )

                                    val overlayOptions = GroundOverlayOptions()
                                        .image(
                                            BitmapDescriptorFactory.fromBitmap(
                                                R.drawable.right_arrow.toBitmap(
                                                    requireContext()
                                                )!!
                                            )
                                        )
                                        .positionFromBounds(
                                            LatLngBounds.Builder().apply {
                                                include(kaaba)
                                                include(LatLng(30.9790438, 31.1675868))
                                            }.build()
                                        )
                                        .bearing(
                                            prefrencesHelper.getQiblaFromLocal()?.toFloat() ?: 135f
                                        )
                                        .visible(true)
                                    mGoogleMap.addGroundOverlay(overlayOptions)
                                }
                            }

                    }
                }
        }
        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException) {
                try {
                    exception.startResolutionForResult(
                        requireActivity(),
                        REQUEST_CHECK_SETTINGS
                    )
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                }

            }
        }
    }
}