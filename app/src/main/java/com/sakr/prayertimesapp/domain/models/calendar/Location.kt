package com.sakr.prayertimesapp.domain.models.calendar


import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("latitude")
    val latitude: Double, // 39.70421229999999
    @SerializedName("longitude")
    val longitude: Double // -86.39943869999999
)