package com.sakr.prayertimesapp.domain.usecases

import com.sakr.prayertimesapp.domain.repositories.MainRepository

class GetPrayerTimesFromRemoteUseCase(private val mainRepository: MainRepository) {
    suspend operator fun invoke(
        year: Int,
        month: Int,
        latitude: Double,
        longitude: Double,
        method: Int = 2,
    ) = mainRepository.getPrayerTimesFromRemote(year, month, latitude, longitude, method)
}