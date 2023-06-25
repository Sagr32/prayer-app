package com.sakr.prayertimesapp.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.sakr.prayertimesapp.data.local.DateConverter
import java.util.*

@Entity(tableName = "prayer_times")
data class PrayerTimesEntity(
    // will represent day and index of the day in the month
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "fajr")
    val fajr: String,
    @ColumnInfo(name = "dhuhr")
    val dhuhr: String,
    @ColumnInfo(name = "asr")
    val asr: String,
    @ColumnInfo(name = "maghrib")
    val maghrib: String,
    @ColumnInfo(name = "isha")
    val isha: String,
    @ColumnInfo(name = "date")
    val date: Date,
    @ColumnInfo(name = "hijri_date")
    val hijriDate: String,
    @ColumnInfo(name = "longitude")
    val longitude: Double,
    @ColumnInfo(name = "latitude")
    val latitude: Double,
    @ColumnInfo(name = "dayofweek")
    val dayOfWeek: String,
    @ColumnInfo(name = "dayofweek_ar")
    val dayOfWeekAr: String,
)