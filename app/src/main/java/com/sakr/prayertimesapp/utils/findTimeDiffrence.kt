package com.sakr.prayertimesapp.utils

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

fun String.findTimeDifference(secondTime: String): Long {
    val firstTimeWithoutEEST = this.split(" ")[0]
    val formatIn24 = SimpleDateFormat("HH:mm", Locale.ENGLISH)
    val firstTimeIn24 = formatIn24.parse(firstTimeWithoutEEST)
    val secondTimeIn24 = formatIn24.parse(secondTime)
    val difference = firstTimeIn24.time - secondTimeIn24.time
    val days = (difference / (1000 * 60 * 60 * 24));
    val hours = ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
    val min = (difference - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours)) / (1000 * 60);


    return difference
}