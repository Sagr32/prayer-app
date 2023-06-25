package com.sakr.prayertimesapp.app.ui.welcome

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.Task
import com.sakr.prayertimesapp.R
import com.sakr.prayertimesapp.app.viewmodel.MainViewModel
import com.sakr.prayertimesapp.databinding.FragmentWelcomeBinding
import com.sakr.prayertimesapp.databinding.ProgressOverlayBinding
import com.sakr.prayertimesapp.domain.models.GenericApiResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.security.Permission
import javax.inject.Inject

@AndroidEntryPoint
class WelcomeFragment : Fragment() {

    private lateinit var binding: FragmentWelcomeBinding
    private val REQUEST_CHECK_SETTINGS = 100
    private val REQUEST_LOCATION_PERMISSION = 1
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var progresOverlay: ProgressOverlayBinding

    @Inject
    lateinit var viewModel: MainViewModel

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.e("TAG", "onActivityResult: $requestCode , result $resultCode")
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            checkDeviceLocationSettings()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWelcomeBinding.inflate(inflater)
        progresOverlay = binding.progressOverlayLayout
        observePrayerTimes()
//        observePrayerTimes()
        checkLocationPermission()
        binding.btnNext.setOnClickListener {
            checkDeviceLocationSettings()
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun observePrayerTimes() {
        lifecycleScope.launch {
            viewModel.prayerTimes.collect {
                when (it) {
                    is GenericApiResponse.Error -> {
                        progresOverlay.parent.visibility = View.GONE
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    is GenericApiResponse.Loading -> {
                        progresOverlay.parent.visibility = View.VISIBLE
                    }
                    is GenericApiResponse.Success -> {
                        viewModel.setIsFirstTime(false)
                        progresOverlay.parent.visibility = View.GONE
                        findNavController().navigate(R.id.action_welcomeFragment_to_homeFragment)
                    }
                    null -> {}
                }
            }
        }
    }

    private fun checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            val permissionLauncher = registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted ->
                if (isGranted) {
                    checkDeviceLocationSettings()
                } else {
//                    Toast.makeText(requireContext(), "Permission Denied", Toast.LENGTH_SHORT).show()
                }
            }

            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            return
        } else {
            checkDeviceLocationSettings()
            Toast.makeText(requireContext(), "Permission Granted", Toast.LENGTH_SHORT).show()
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
                        viewModel.saveLocation(currentLatLng)
                        viewModel.getPrayerTimesCalendar()
                        viewModel.getQiblaFromRemote(
                            currentLatLng.latitude,
                            currentLatLng.longitude
                        )
                    } else {
                        fusedLocationClient.getCurrentLocation(
                            LocationRequest.PRIORITY_HIGH_ACCURACY,
                            null
                        )
                            .addOnSuccessListener { currentLocation: android.location.Location? ->
                                if (currentLocation != null) {
                                    val currentLatLng =
                                        LatLng(currentLocation.latitude, currentLocation.longitude)
                                    viewModel.saveLocation(currentLatLng)
                                    viewModel.getPrayerTimesCalendar()


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
