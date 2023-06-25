package com.sakr.prayertimesapp.domain.models.calendar


import com.google.gson.annotations.SerializedName

data class Designation(
    @SerializedName("abbreviated")
    val abbreviated: String, // AD
    @SerializedName("expanded")
    val expanded: String // Anno Domini
)