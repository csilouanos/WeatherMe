package com.example.weatherme.models

import com.google.gson.annotations.SerializedName

data class CityWeatherResponse(
    @SerializedName("location") var location: CityLocation,
    @SerializedName("current") var weather: CityWeather
)

data class CityWeather(
    @SerializedName("temp_c") var celsiusTemperature: Double,
    @SerializedName("humidity") var humidity: Int,
    @SerializedName("condition") var condition: WeatherCondition
)

data class CityLocation(
    @SerializedName("name") var name: String,
    @SerializedName("region") var region: String,
    @SerializedName("country") var country: String,
    @SerializedName("localtime") var localtime: String
)

data class WeatherCondition(
    @SerializedName("text") var conditionDescription: String,
    @SerializedName("icon") var conditionIcon: String,
    @SerializedName("code") var conditionCode: Int
)