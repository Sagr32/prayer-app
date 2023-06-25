package com.sakr.prayertimesapp.domain.models.calendar


import com.google.gson.annotations.SerializedName

data class WeekdayX(
    @SerializedName("ar")
    val ar: String, // الخميس
    @SerializedName("en")
    val en: String // Al Khamees
)