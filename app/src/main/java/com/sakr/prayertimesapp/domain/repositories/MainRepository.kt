package com.sakr.prayertimesapp.domain.repositories

import com.sakr.prayertimesapp.domain.models.GenericApiResponse
import com.sakr.prayertimesapp.domain.models.PrayerTimesEntity
import com.sakr.prayertimesapp.domain.models.UserLatLng
import com.sakr.prayertimesapp.domain.models.calendar.PrayerTimeCalendarResponse
import com.sakr.prayertimesapp.domain.models.qibla.QiblaResponse
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    suspend fun getQibla(
        latitude: Double,
        longitude: Double,
    ): GenericApiResponse<QiblaResponse>

    suspend fun getPrayerTimesFromLocal(
    ): List<PrayerTimesEntity>

    suspend fun getPrayerTimesFromRemote(
        year: Int,
        month: Int,
        latitude: Double,
        longitude: Double,
        method: Int = 2,
    ): GenericApiResponse<PrayerTimeCalendarResponse>

    suspend fun savePrayerTimesToLocal(
        prayerTimesEntity: List<PrayerTimesEntity>
    )

    fun getIsUserFirstTime(): Boolean

    fun setIsUserFirstTime(isFirstTime: Boolean)

    fun saveUserLatLng(lat: Double, lng: Double)

    fun getUserLatLng(): UserLatLng?
    fun getAddress(latitude: Double, longitude: Double): String?

    fun getQiblaFromLocal(): Double?
    fun saveQiblaDirection(qiblaDirection: Double)

}