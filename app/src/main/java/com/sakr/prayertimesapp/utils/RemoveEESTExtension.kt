package com.sakr.prayertimesapp.utils

import java.text.SimpleDateFormat
import java.util.*


fun String.removeEESTExtension(): String {

    val dateWithEEST = this.split(" ")[0]
    val formatIn24 = SimpleDateFormat("HH:mm", Locale.ENGLISH)
    val timeIn24Format = formatIn24.parse(dateWithEEST)
    val formatIn12 = SimpleDateFormat("h:mm a", Locale.ENGLISH)
    return formatIn12.format(timeIn24Format!!)

}