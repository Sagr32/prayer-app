package com.sakr.prayertimesapp.domain.models.calendar


import com.google.gson.annotations.SerializedName

data class Meta(
    @SerializedName("latitude")
    val latitude: Double, // 30.974876
    @SerializedName("latitudeAdjustmentMethod")
    val latitudeAdjustmentMethod: String, // ANGLE_BASED
    @SerializedName("longitude")
    val longitude: Double, // 31.168034
    @SerializedName("method")
    val method: Method,
    @SerializedName("midnightMode")
    val midnightMode: String, // STANDARD
    @SerializedName("offset")
    val offset: Offset,
    @SerializedName("school")
    val school: String, // STANDARD
    @SerializedName("timezone")
    val timezone: String // Africa/Cairo
)