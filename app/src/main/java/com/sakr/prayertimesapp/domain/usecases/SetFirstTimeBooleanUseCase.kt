package com.sakr.prayertimesapp.domain.usecases

import com.sakr.prayertimesapp.domain.repositories.MainRepository

class SetFirstTimeBooleanUseCase(private val mainRepository: MainRepository) {
     operator fun invoke(firstTime: Boolean) = mainRepository.setIsUserFirstTime(firstTime)
}