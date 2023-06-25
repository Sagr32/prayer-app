package com.sakr.prayertimesapp.data.di

import com.sakr.prayertimesapp.app.viewmodel.MainViewModel
import com.sakr.prayertimesapp.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class ViewModelModule {

    @Provides
    fun provideMainViewModel(
        getPrayerTimesCalendarUseCase: GetPrayerTimesFromRemoteUseCase,
        getPrayerTimesFromLocalUseCase: GetPrayerTimesFromLocalUseCase,
        savePrayerTime: SavePrayerTimeUseCase,
        userLatLngUseCase: UserLatLngUseCase,
        gerAddressUseCase: GetAddressUseCase,
        getQiblaDirectionUseCase: GetQiblaDirectionUseCase,
        saveQiblaDirection: SaveQiblaDirection,
        setFirstTimeBooleanUseCase: SetFirstTimeBooleanUseCase

    ): MainViewModel {
        return MainViewModel(
            getPrayerTimesCalendarUseCase,
            getPrayerTimesFromLocalUseCase,
            savePrayerTime,
            userLatLngUseCase,
            gerAddressUseCase,
            getQiblaDirectionUseCase,
            saveQiblaDirection,
            setFirstTimeBooleanUseCase

        )
    }
}