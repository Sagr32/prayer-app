package com.sakr.prayertimesapp.domain.models.calendar


import com.google.gson.annotations.SerializedName

data class Hijri(
    @SerializedName("date")
    val date: String, // 12-11-1444
    @SerializedName("day")
    val day: String, // 12
    @SerializedName("designation")
    val designation: DesignationX,
    @SerializedName("format")
    val format: String, // DD-MM-YYYY
    @SerializedName("holidays")
    val holidays: List<String>,
    @SerializedName("month")
    val month: MonthX,
    @SerializedName("weekday")
    val weekday: WeekdayX,
    @SerializedName("year")
    val year: String // 1444
)