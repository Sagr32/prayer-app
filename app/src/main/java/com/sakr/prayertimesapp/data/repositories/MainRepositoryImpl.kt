package com.sakr.prayertimesapp.data.repositories

import android.location.Address
import android.location.Geocoder
import android.util.Log
import com.sakr.prayertimesapp.data.local.AppDatabase
import com.sakr.prayertimesapp.data.local.PreferencesHelper
import com.sakr.prayertimesapp.data.remote.ApiService
import com.sakr.prayertimesapp.data.remote.safeApiCall
import com.sakr.prayertimesapp.domain.models.GenericApiResponse
import com.sakr.prayertimesapp.domain.models.PrayerTimesEntity
import com.sakr.prayertimesapp.domain.models.UserLatLng
import com.sakr.prayertimesapp.domain.models.calendar.PrayerTimeCalendarResponse
import com.sakr.prayertimesapp.domain.models.qibla.QiblaResponse
import com.sakr.prayertimesapp.domain.repositories.MainRepository
import kotlinx.coroutines.flow.Flow
import java.io.IOException
import java.util.*

class MainRepositoryImpl(
    private val apiService: ApiService,
    private val appDatabase: AppDatabase,
    private val preferencesHelper: PreferencesHelper,
    private val geocoder: Geocoder
) :
    MainRepository {
    override suspend fun getPrayerTimesFromRemote(
        year: Int,
        month: Int,
        latitude: Double,
        longitude: Double,
        method: Int
    ): GenericApiResponse<PrayerTimeCalendarResponse> {
        return safeApiCall {
            apiService.getPrayerTimesCalendar(
                year,
                month,
                latitude,
                longitude,
                method
            )
        }
    }

    override suspend fun getQibla(latitude: Double, longitude: Double): GenericApiResponse<QiblaResponse> {
        return safeApiCall {
            apiService.getQibla(
                latitude,
                longitude
            )
        }
    }

    override suspend fun getPrayerTimesFromLocal(): List<PrayerTimesEntity> {
        val day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        return appDatabase.prayerTimesDao().getTimesForAWeek(day)
    }


    override suspend fun savePrayerTimesToLocal(prayerTimesEntity: List<PrayerTimesEntity>) {
        appDatabase.prayerTimesDao().insert(prayerTimesEntity)
    }

    override fun getIsUserFirstTime(): Boolean {
        return preferencesHelper.getIsUserFirstTime()
    }

    override fun setIsUserFirstTime(isFirstTime: Boolean) {
        preferencesHelper.saveIsUserFirstTime(isFirstTime)
    }

    override fun saveUserLatLng(lat: Double, lng: Double) {
        preferencesHelper.saveUserLatLng(lat, lng)
    }

    override fun getUserLatLng(): UserLatLng? {
        return preferencesHelper.getUserLatLng()
    }

    override fun getAddress(latitude: Double, longitude: Double): String? {
        val result = StringBuilder()
        Log.e("TAG", "getAddress:start ")
        try {

            Log.e("TAG", "getAddress:try$latitude $longitude    ")
            val addresses: List<Address>? = geocoder.getFromLocation(latitude, longitude, 1)
            if (addresses!!.isNotEmpty()) {
                val address: Address = addresses[0]
                result.append(address.locality)
//                result.append(address.countryName)
            } else {
                Log.e("TAG", "getAddress: empty")
            }
        } catch (e: IOException) {
            Log.e("TAG", "getAddress:catch ${e.localizedMessage} ")

            Log.e("tag", e.localizedMessage)
        }
        Log.e("TAG", "getAddress: ${result.toString()}", )
        return result.toString()
    }

    override fun getQiblaFromLocal(): Double? {
        return preferencesHelper.getQiblaFromLocal()
    }

    override fun saveQiblaDirection(qiblaDirection: Double) {
        preferencesHelper.saveQiblaToLocal(qiblaDirection)
    }

}