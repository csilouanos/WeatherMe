package com.example.weatherme.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weatherme.models.CityWeatherResponse
import com.example.weatherme.models.Weatherable
import com.example.weatherme.models.WeatherableDetails

@Entity
data class Weather(
    @PrimaryKey @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "country") val country: String,
    @ColumnInfo(name = "weather_condition") val weatherCondition: String,
    @ColumnInfo(name = "weather_condition_icon") val weatherConditionIcon: String,
    @ColumnInfo(name = "weather_celsius") val weatherCelsius: Double,
    @ColumnInfo(name = "humidity") val humidity: Int,
    @ColumnInfo(name = "is_saved") var isSaved: Boolean = false
) : WeatherableDetails, Weatherable {

    override val titleName: String
        get() = name

    override val subtitleCountry: String
        get() = country

    override val weatherDescription: String
        get() = weatherCondition

    override val icon: String
        get() = "http:$weatherConditionIcon"

    override val temperature: String
        get() = "${weatherCelsius}°C"

    override val humidityDescription: String
        get() = "$humidity%"

    companion object {
        private val TAG = Weather::class.simpleName

        fun generateDBObject(response: CityWeatherResponse): Weather {
            response.run {
                return Weather(
                    location.name,
                    location.country,
                    weather.condition.conditionDescription,
                    weather.condition.conditionIcon,
                    weather.celsiusTemperature,
                    weather.humidity
                )
            }
        }
    }

}