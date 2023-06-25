package com.sakr.prayertimesapp.data.di

import android.content.Context
import androidx.room.Room
import com.sakr.prayertimesapp.data.local.AppDatabase
import com.sakr.prayertimesapp.data.local.PreferencesHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "prayer_times"
        ).build(
        )
    }

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): PreferencesHelper {
        return PreferencesHelper(context)
    }

    @Provides
    fun provideGeocoder(@ApplicationContext context: Context): android.location.Geocoder {
        return android.location.Geocoder(context)
    }

}