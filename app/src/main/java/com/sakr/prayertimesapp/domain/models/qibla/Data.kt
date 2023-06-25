package com.sakr.prayertimesapp.domain.models.qibla


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("direction")
    val direction: Double, // 251.39286724879
    @SerializedName("latitude")
    val latitude: Double, // 25.4106386
    @SerializedName("longitude")
    val longitude: Double // 51.1846025
)