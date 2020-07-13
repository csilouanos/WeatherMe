package com.example.weatherme.mvvm.repositories

import android.app.Application
import com.example.weatherme.database.AppDatabase
import com.example.weatherme.database.Weather
import com.example.weatherme.models.CityWeatherResponse
import com.example.weatherme.network.NetworkManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

@ExperimentalCoroutinesApi
class WeatherRepository(private val application: Application) {

    fun getWeather(city: String): Flow<CityWeatherResponse> {
        return flow {
            val weather = NetworkManager.shared.networkService.getWeatherByCity(city)
            emit(weather)
        }.flowOn(Dispatchers.IO) //Dispatchers.IO indicates that this coroutine should be executed on a thread reserved for I/O operations.
    }

    fun findNonSavedCitiesFromDB(city: String): Flow<List<Weather>> {
        return flow {
            val weather = AppDatabase.shared.weatherDao().findNonSavedCities(city)
            emit(weather)
        }.distinctUntilChanged().flowOn(Dispatchers.IO)
    }

    fun findSavedCitiesFromDB(): Flow<List<Weather>> {
        return flow {
            val weather = AppDatabase.shared.weatherDao().findAllSavedCities()
            emit(weather)
        }.flowOn(Dispatchers.IO)
    }

    fun getAllCities() : Flow<List<Weather>> {
        return flow {
            val weather = AppDatabase.shared.weatherDao().getAll()
            emit(weather)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun insertOrUpdateWeatherEntry(weather: CityWeatherResponse) {
        weather.run {
            val dbWeather = Weather.generateDBObject(weather)
            AppDatabase.shared.weatherDao().insertOrUpdate(dbWeather)
        }
    }

    suspend fun markEntryAsSaved(weather: Weather) {
        AppDatabase.shared.weatherDao().setSaved(weather.name)
    }
}