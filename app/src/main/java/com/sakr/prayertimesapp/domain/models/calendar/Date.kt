package com.sakr.prayertimesapp.domain.models.calendar


import com.google.gson.annotations.SerializedName

data class Date(
    @SerializedName("gregorian")
    val gregorian: Gregorian,
    @SerializedName("hijri")
    val hijri: Hijri,
    @SerializedName("readable")
    val readable: String, // 01 Jun 2023
    @SerializedName("timestamp")
    val timestamp: String // 1685599261
)