package com.example.weatherme.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weatherme.models.CityWeatherResponse
import com.example.weatherme.models.Weatherable
import com.example.weatherme.models.WeatherableDetails

@Entity
data class Weather(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "country") val country: String,
    @ColumnInfo(name = "weather_condition") val weatherCondition: String,
    @ColumnInfo(name = "weather_condition_icon") val weatherConditionIcon: String,
    @ColumnInfo(name = "weather_celsius") val weatherCelsius: Double,
    @ColumnInfo(name = "humidity") val humidity: Int
) : WeatherableDetails, Weatherable {

    @PrimaryKey(autoGenerate = true)
    var wid: Int = 0

    override val titleName: String
        get() = name

    override val subtitleCountry: String
        get() = country

    override val weatherDescription: String
        get() = weatherCondition

    override val icon: String
        get() = weatherConditionIcon

    override val temperature: String
        get() = weatherCelsius.toString() //TODO: Format.

    override val humidityDescription: String
        get() = humidity.toString() //TODO: Format.

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