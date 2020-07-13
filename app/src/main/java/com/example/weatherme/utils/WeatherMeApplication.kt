package com.example.weatherme.utils

import android.app.Application
import androidx.room.Room
import com.example.weatherme.database.AppDatabase
import com.example.weatherme.di.repositories
import com.example.weatherme.di.viewModels
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WeatherMeApplication : Application() {

    init {
        shared = this
    }

    @ExperimentalCoroutinesApi
    override fun onCreate() {
        super.onCreate()
        configureKoin()
    }

    @ExperimentalCoroutinesApi
    private fun configureKoin() {
        startKoin {
            androidContext(this@WeatherMeApplication)
            modules(viewModels, repositories)
        }
    }

    companion object {
        lateinit var shared: WeatherMeApplication
            private set
    }

    val database = Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java, "weather-db"
    ).enableMultiInstanceInvalidation()
        .build()
}