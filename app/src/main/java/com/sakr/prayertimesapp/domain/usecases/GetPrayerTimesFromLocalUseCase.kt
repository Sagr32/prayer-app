package com.sakr.prayertimesapp.domain.usecases

import com.sakr.prayertimesapp.domain.repositories.MainRepository

class GetPrayerTimesFromLocalUseCase(private val mainRepository: MainRepository) {
    suspend operator fun invoke() = mainRepository.getPrayerTimesFromLocal()
}