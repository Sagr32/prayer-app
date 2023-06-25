package com.sakr.prayertimesapp.domain.models.calendar


import com.google.gson.annotations.SerializedName

data class Gregorian(
    @SerializedName("date")
    val date: String, // 01-06-2023
    @SerializedName("day")
    val day: String, // 01
    @SerializedName("designation")
    val designation: Designation,
    @SerializedName("format")
    val format: String, // DD-MM-YYYY
    @SerializedName("month")
    val month: Month,
    @SerializedName("weekday")
    val weekday: Weekday,
    @SerializedName("year")
    val year: String // 2023
)