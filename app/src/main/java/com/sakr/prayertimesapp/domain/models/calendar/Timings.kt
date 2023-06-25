package com.sakr.prayertimesapp.domain.models.calendar


import com.google.gson.annotations.SerializedName

data class Timings(
    @SerializedName("Asr")
    val asr: String, // 16:31 (EEST)
    @SerializedName("Dhuhr")
    val dhuhr: String, // 12:53 (EEST)
    @SerializedName("Fajr")
    val fajr: String, // 04:35 (EEST)
    @SerializedName("Firstthird")
    val firstthird: String, // 23:13 (EEST)
    @SerializedName("Imsak")
    val imsak: String, // 04:25 (EEST)
    @SerializedName("Isha")
    val isha: String, // 21:12 (EEST)
    @SerializedName("Lastthird")
    val lastthird: String, // 02:33 (EEST)
    @SerializedName("Maghrib")
    val maghrib: String, // 19:54 (EEST)
    @SerializedName("Midnight")
    val midnight: String, // 00:53 (EEST)
    @SerializedName("Sunrise")
    val sunrise: String, // 05:53 (EEST)
    @SerializedName("Sunset")
    val sunset: String // 19:54 (EEST)
)