package com.sakr.prayertimesapp.domain.models.qibla


import com.google.gson.annotations.SerializedName

data class QiblaResponse(
    @SerializedName("code")
    val code: Int, // 200
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("status")
    val status: String // OK
)