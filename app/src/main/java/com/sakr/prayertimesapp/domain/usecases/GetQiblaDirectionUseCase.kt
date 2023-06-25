package com.sakr.prayertimesapp.domain.usecases

import com.sakr.prayertimesapp.domain.repositories.MainRepository

class GetQiblaDirectionUseCase(private val mainRepository: MainRepository) {
    suspend fun getFromRemote(latitude: Double, longitude: Double) =
        mainRepository.getQibla(latitude, longitude)

    suspend fun getFromLocal() = mainRepository.getQiblaFromLocal()
}