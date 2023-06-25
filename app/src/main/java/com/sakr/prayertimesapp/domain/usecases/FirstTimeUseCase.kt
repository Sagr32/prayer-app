package com.sakr.prayertimesapp.domain.usecases

import com.sakr.prayertimesapp.domain.repositories.MainRepository

class FirstTimeUseCase(private val mainRepository: MainRepository) {
    fun getIsUserFirstTime() = mainRepository.getIsUserFirstTime()
    fun setIsUserFirstTime(isFirstTime: Boolean) = mainRepository.setIsUserFirstTime(isFirstTime)
}