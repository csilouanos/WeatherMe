package com.example.weatherme.di

import com.example.weatherme.mvvm.repositories.WeatherRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.dsl.module

@ExperimentalCoroutinesApi
val repositories = module {
    single { WeatherRepository(get()) }
}