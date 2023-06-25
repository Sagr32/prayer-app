package com.sakr.prayertimesapp.domain.usecases

import com.sakr.prayertimesapp.domain.repositories.MainRepository

class GetAddressUseCase(private val mainRepository: MainRepository) {
    operator fun invoke(latitude: Double, longitude: Double): String? {
        return mainRepository.getAddress(latitude, longitude)
    }
}