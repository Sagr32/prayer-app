package com.sakr.prayertimesapp.data.di

import com.sakr.prayertimesapp.domain.repositories.MainRepository
import com.sakr.prayertimesapp.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    fun provideGetPrayerTimeUseCase(
        mainRepository: MainRepository
    ): GetPrayerTimesFromRemoteUseCase {
        return GetPrayerTimesFromRemoteUseCase(mainRepository)
    }

    @Provides
    fun provideGetPrayerTimesFromLocalUseCase(
        mainRepository: MainRepository
    ): GetPrayerTimesFromLocalUseCase {
        return GetPrayerTimesFromLocalUseCase(mainRepository)
    }


    @Provides
    fun provideSavePrayerTimeUseCase(
        mainRepository: MainRepository
    ): SavePrayerTimeUseCase {
        return SavePrayerTimeUseCase(mainRepository)
    }

    @Provides
    fun provideUserLatLngUseCase(
        mainRepository: MainRepository
    ): UserLatLngUseCase {
        return UserLatLngUseCase(mainRepository)
    }

    @Provides
    fun provideGetAddressUseCase(
        mainRepository: MainRepository
    ): GetAddressUseCase {
        return GetAddressUseCase(mainRepository)
    }


    @Provides
    fun provideGetQiblaDirectionUseCase(
        mainRepository: MainRepository
    ): GetQiblaDirectionUseCase {
        return GetQiblaDirectionUseCase(mainRepository)
    }

    @Provides
    fun provideSaveQiblaDirectionUseCase(
        mainRepository: MainRepository
    ): SaveQiblaDirection {
        return SaveQiblaDirection(mainRepository)
    }

    @Provides
    fun provideSetFirstTimeBooleanUseCase(
        mainRepository: MainRepository
    ): SetFirstTimeBooleanUseCase {
        return SetFirstTimeBooleanUseCase(mainRepository)
    }


}
