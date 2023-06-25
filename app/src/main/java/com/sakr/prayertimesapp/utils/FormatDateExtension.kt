package com.sakr.prayertimesapp.utils

import java.text.SimpleDateFormat

fun String.formatDate() {
    val SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
    val date = SimpleDateFormat.parse(this)



}