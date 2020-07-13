package com.example.weatherme.models

interface WeatherableDetails: Weatherable {
    val subtitleCountry: String
    val weatherDescription: String
    val icon: String
    val humidityDescription: String
}

interface Weatherable {
    val titleName: String
    val temperature: String
}