package com.sakr.prayertimesapp.domain.usecases

import com.sakr.prayertimesapp.domain.models.PrayerTimesEntity
import com.sakr.prayertimesapp.domain.repositories.MainRepository

class SavePrayerTimeUseCase(private val mainRepository: MainRepository) {
    suspend operator fun invoke(
        prayerTimesEntityList: List<PrayerTimesEntity>
    ) = mainRepository.savePrayerTimesToLocal(prayerTimesEntityList)
}