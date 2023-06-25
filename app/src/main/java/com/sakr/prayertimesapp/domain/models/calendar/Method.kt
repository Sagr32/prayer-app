package com.sakr.prayertimesapp.domain.models.calendar


import com.google.gson.annotations.SerializedName

data class Method(
    @SerializedName("id")
    val id: Int, // 2
    @SerializedName("location")
    val location: Location,
    @SerializedName("name")
    val name: String, // Islamic Society of North America (ISNA)
    @SerializedName("params")
    val params: Params
)