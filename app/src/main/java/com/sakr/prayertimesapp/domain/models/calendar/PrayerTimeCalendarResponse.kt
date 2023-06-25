package com.sakr.prayertimesapp.domain.models.calendar


import com.google.gson.annotations.SerializedName

data class PrayerTimeCalendarResponse(
    @SerializedName("code")
    val code: Int, // 200
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("status")
    val status: String // OK
)