package com.sakr.prayertimesapp.domain.models.calendar


import com.google.gson.annotations.SerializedName

data class Month(
    @SerializedName("en")
    val en: String, // June
    @SerializedName("number")
    val number: Int // 6
)