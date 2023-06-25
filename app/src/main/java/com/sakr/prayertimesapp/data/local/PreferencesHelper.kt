package com.sakr.prayertimesapp.data.local

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.sakr.prayertimesapp.domain.models.UserLatLng
import javax.inject.Inject

class PreferencesHelper @Inject constructor(
    private val context: Context,
) {

    private val sharedPref: SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)

    fun saveLatitude(latitude: String) {
        sharedPref.edit().putString("latitude", latitude).apply()
    }

    fun saveLongitude(longitude: String) {
        sharedPref.edit().putString("longitude", longitude).apply()
    }

    fun getLatitude(): String? {
        return sharedPref.getString("latitude", null)
    }

    fun getLongitude(): String? {
        return sharedPref.getString("longitude", null)
    }

    fun saveIsUserFirstTime(isUserFirstTime: Boolean) {
        sharedPref.edit().putBoolean("isUserFirstTime", isUserFirstTime).apply()
    }

    fun getIsUserFirstTime(): Boolean {
        return sharedPref.getBoolean("isUserFirstTime", true)
    }

    fun saveUserLatLng(lat: Double, lng: Double) {
        sharedPref.edit().putString("lat", lat.toString()).apply()
        sharedPref.edit().putString("lng", lng.toString()).apply()
    }

    fun getUserLatLng(): UserLatLng? {
        val lat = sharedPref.getString("lat", null)?.toDouble()
        val lng = sharedPref.getString("lng", null)?.toDouble()
        return UserLatLng(lat, lng)
    }

    fun getQiblaFromLocal(): Double? {
        return sharedPref.getString("qibla", null)?.toDouble()

    }

    fun saveQiblaToLocal(qibla: Double) {
        sharedPref.edit().putString("qibla", qibla.toString()).apply()
    }

}