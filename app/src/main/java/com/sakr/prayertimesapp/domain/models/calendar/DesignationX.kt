package com.sakr.prayertimesapp.domain.models.calendar


import com.google.gson.annotations.SerializedName

data class DesignationX(
    @SerializedName("abbreviated")
    val abbreviated: String, // AH
    @SerializedName("expanded")
    val expanded: String // Anno Hegirae
)