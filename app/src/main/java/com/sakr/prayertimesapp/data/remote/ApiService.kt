package com.sakr.prayertimesapp.data.remote

import com.sakr.prayertimesapp.domain.models.GenericApiResponse
import com.sakr.prayertimesapp.domain.models.calendar.PrayerTimeCalendarResponse
import com.sakr.prayertimesapp.domain.models.qibla.QiblaResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {
    @GET("calendar/{year}/{month}")
    suspend fun getPrayerTimesCalendar(
        @Path("year") year: Int,
        @Path("month") month: Int,
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("method") method: Int = 2,
    ): Response<PrayerTimeCalendarResponse>

    @GET("qibla/{latitude}/{longitude}")
    suspend fun getQibla(
        @Path("latitude") latitude: Double,
        @Path("longitude") longitude: Double,
    ): Response<QiblaResponse>
}