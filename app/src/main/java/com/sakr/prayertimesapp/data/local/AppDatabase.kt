package com.sakr.prayertimesapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sakr.prayertimesapp.domain.models.PrayerTimesEntity


@Database(entities = [PrayerTimesEntity::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun prayerTimesDao(): PrayerTimesDao
}