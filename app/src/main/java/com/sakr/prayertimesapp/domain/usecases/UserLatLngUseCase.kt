package com.sakr.prayertimesapp.domain.usecases

import com.sakr.prayertimesapp.domain.repositories.MainRepository

class UserLatLngUseCase(private val mainRepository: MainRepository) {

    fun saveUserLatLng(lat: Double, lng: Double) {
        mainRepository.saveUserLatLng(lat, lng)
    }

    fun getUserLatLng() = mainRepository.getUserLatLng()
}