package com.example.weatherme.network

import com.example.weatherme.models.CityWeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RestClientService {
    @GET("current.json")
    suspend fun getWeatherByCity(@Query("q") city: String): CityWeatherResponse
}