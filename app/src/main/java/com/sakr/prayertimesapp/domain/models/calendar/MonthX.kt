package com.sakr.prayertimesapp.domain.models.calendar


import com.google.gson.annotations.SerializedName

data class MonthX(
    @SerializedName("ar")
    val ar: String, // ذوالقعدة
    @SerializedName("en")
    val en: String, // Dhū al-Qaʿdah
    @SerializedName("number")
    val number: Int // 11
)