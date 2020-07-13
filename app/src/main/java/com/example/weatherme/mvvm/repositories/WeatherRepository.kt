package com.example.weatherme.mvvm.repositories

import com.example.weatherme.database.Weather
import com.example.weatherme.models.CityWeatherResponse
import com.example.weatherme.models.Weatherable
import com.example.weatherme.models.WeatherableDetails
import com.example.weatherme.network.NetworkManager
import com.example.weatherme.utils.WeatherMeApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

@ExperimentalCoroutinesApi
class WeatherRepository(private val application: WeatherMeApplication) {
//    fun getWeather(city: String): Deferred<CityResponse> {
//        return NetworkManager.shared.networkService.getWeatherByCity(city)
//    }

    fun getWeather(city: String): Flow<CityWeatherResponse> {
        return flow {
            val weather = NetworkManager.shared.networkService.getWeatherByCity(city)
            emit(weather)
        }.flowOn(Dispatchers.IO) //Dispatchers.IO indicates that this coroutine should be executed on a thread reserved for I/O operations.
    }

    fun getWeatherFromDB(city: String): Flow<List<Weather>> {
        return flow {
            val weather = application.database.weatherDao().findByCityName(city)
            emit(weather)
        }.flowOn(Dispatchers.IO)
    }

    fun getAllCities(): Flow<List<Weather>> {
        return flow {
            val weather = application.database.weatherDao().getAll()
            emit(weather)
        }/*.distinctUntilChanged()*/.flowOn(Dispatchers.IO)
    }

    suspend fun saveWeatherEntry(weather: CityWeatherResponse) {
        weather.run {
            val dbWeather = Weather.generateDBObject(weather)
            application.database.weatherDao().insertWeather(dbWeather)
        }
    }
}