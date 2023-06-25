package com.sakr.prayertimesapp.domain.models.calendar


import com.google.gson.annotations.SerializedName

data class Offset(
    @SerializedName("Asr")
    val asr: Int, // 0
    @SerializedName("Dhuhr")
    val dhuhr: Int, // 0
    @SerializedName("Fajr")
    val fajr: Int, // 0
    @SerializedName("Imsak")
    val imsak: Int, // 0
    @SerializedName("Isha")
    val isha: Int, // 0
    @SerializedName("Maghrib")
    val maghrib: Int, // 0
    @SerializedName("Midnight")
    val midnight: Int, // 0
    @SerializedName("Sunrise")
    val sunrise: Int, // 0
    @SerializedName("Sunset")
    val sunset: Int // 0
)