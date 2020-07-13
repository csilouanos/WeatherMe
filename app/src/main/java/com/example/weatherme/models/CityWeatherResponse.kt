package com.example.weatherme.models

import com.google.gson.annotations.SerializedName

data class CityWeatherResponse(
    @SerializedName("location") var location: CityLocation,
    @SerializedName("current") var weather: CityWeather
): WeatherableDetails {
    override val titleName: String
        get() = location.name

    override val subtitleCountry: String
        get() = location.country

    override val weatherDescription: String
        get() = weather.condition.conditionDescription

    override val icon: String
        get() = weather.condition.conditionIcon

    override val temperature: String
        get() = weather.celsiusTemperature.toString() //TODO: Add formatter

    override val humidityDescription: String
        get() = weather.humidity.toString() //TODO: Add formatter
}

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