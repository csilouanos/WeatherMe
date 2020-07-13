package com.example.weatherme.di

import com.example.weatherme.mvvm.view_models.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.dsl.module

@ExperimentalCoroutinesApi
val viewModels = module {
    factory { MainViewModel(get(), get()) }
}