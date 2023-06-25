package com.sakr.prayertimesapp.app.ui.qibla

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sakr.prayertimesapp.data.local.PreferencesHelper
import com.sakr.prayertimesapp.databinding.FragmentQiblaBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class QiblaFragment : Fragment(), SensorEventListener {
    private var currentDegree = 0f
    private var sensor: Sensor? = null
    private lateinit var binding: FragmentQiblaBinding

    @Inject
    lateinit var preferencesHelper: PreferencesHelper


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQiblaBinding.inflate(inflater, container, false)

        val sensorManager =
            requireActivity().getSystemService(AppCompatActivity.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION)
        sensorManager.registerListener(
            this, sensor, SensorManager.SENSOR_DELAY_GAME
        )
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val qiblaDirection = preferencesHelper.getQiblaFromLocal()?.toFloat() ?: 0F

        val degree = event!!.values[0].toFloat()

        val ra = RotateAnimation(
            currentDegree + 45, qiblaDirection, Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        ra.duration = 120
        ra.fillAfter = true
        binding.ivQiblaDirection.startAnimation(ra)
        currentDegree = -degree
        Log.e("TAG", "NewTag1:$currentDegree ")
        Log.e("TAG", "NewTag2:$degree ")


    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {


    }


    private fun adjustArrowQiblat() {
        var currentNeedleDegree = 0F
        val qiblaDegree = 136.09149F
        val rotateNeedle = RotateAnimation(
            currentNeedleDegree, -qiblaDegree, Animation.RELATIVE_TO_SELF,
            0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        ).apply {
            duration = 1000
        }
        currentNeedleDegree = qiblaDegree

        binding.ivQiblaDirection.startAnimation(rotateNeedle)
    }


}