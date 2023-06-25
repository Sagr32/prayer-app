package com.sakr.prayertimesapp.domain.models.calendar


import com.google.gson.annotations.SerializedName

data class Params(
    @SerializedName("Fajr")
    val fajr: Int, // 15
    @SerializedName("Isha")
    val isha: Int // 15
)