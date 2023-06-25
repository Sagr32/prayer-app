package com.sakr.prayertimesapp.domain.usecases

import com.sakr.prayertimesapp.domain.repositories.MainRepository

class SaveQiblaDirection(private val mainRepository: MainRepository) {
   operator fun invoke(qiblaDirection: Double) = mainRepository.saveQiblaDirection(qiblaDirection)
}