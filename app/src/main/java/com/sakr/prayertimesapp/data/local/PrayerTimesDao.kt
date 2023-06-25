package com.sakr.prayertimesapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sakr.prayertimesapp.domain.models.PrayerTimesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PrayerTimesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(prayerTimesEntity: List<PrayerTimesEntity>)

    @Query("SELECT * FROM prayer_times")
    suspend fun getAll(): List<PrayerTimesEntity>

    @Query("SELECT * FROM prayer_times WHERE id = :id")
    suspend fun getById(id: Int): PrayerTimesEntity

    @Query("SELECT * FROM prayer_times WHERE date = :date")
    suspend fun getByDate(date: String): PrayerTimesEntity

    @Query("SELECT * FROM prayer_times WHERE id>= :day  LIMIT 7 ")
    suspend fun getTimesForAWeek(day: Int): List<PrayerTimesEntity>


}