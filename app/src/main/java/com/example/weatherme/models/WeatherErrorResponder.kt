package com.example.weatherme.models

import com.example.weatherme.R
import com.example.weatherme.utils.WeatherMeApplication
import java.net.UnknownHostException

class WeatherErrorResponder(apiErrorException: Throwable) {

    // Message that should be visible to user in case of exception.
    val errorMessage: String?

    init {
        errorMessage = if(apiErrorException is UnknownHostException) {
            WeatherMeApplication.shared.getString(R.string.unknown_exception_error)
        } else {
            null
        }
    }

}