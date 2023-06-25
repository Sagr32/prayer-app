package com.sakr.prayertimesapp.data.di

import android.location.Geocoder
import com.sakr.prayertimesapp.data.local.AppDatabase
import com.sakr.prayertimesapp.data.local.PreferencesHelper
import com.sakr.prayertimesapp.data.remote.ApiService
import com.sakr.prayertimesapp.data.repositories.MainRepositoryImpl
import com.sakr.prayertimesapp.domain.repositories.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun provideMainRepository(
        apiService: ApiService,
        appDatabase: AppDatabase,
        preferencesHelper: PreferencesHelper,
        geocoder: Geocoder
    ): MainRepository {
        return MainRepositoryImpl(apiService, appDatabase, preferencesHelper, geocoder)
    }

}