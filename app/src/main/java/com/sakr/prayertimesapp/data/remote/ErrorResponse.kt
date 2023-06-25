package com.sakr.prayertimesapp.data.remote

import com.google.gson.annotations.SerializedName


data class ErrorResponse(
    @SerializedName("code")
    val code: Int, // 404
    @SerializedName("data")
    val data: String, // Not found
    @SerializedName("status")
    val status: String // RESOURCE_NOT_FOUND
)