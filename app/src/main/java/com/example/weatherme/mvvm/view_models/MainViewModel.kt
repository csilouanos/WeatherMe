package com.example.weatherme.mvvm.view_models

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.weatherme.models.WeatherErrorResponder
import com.example.weatherme.models.WeatherableDetails
import com.example.weatherme.mvvm.repositories.WeatherRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.HttpException

@ExperimentalCoroutinesApi
class MainViewModel(application: Application, private val repository: WeatherRepository) :
    AndroidViewModel(application) {

    val cityWeatherLiveData: LiveData<WeatherableDetails>
        get() = _cityWeatherLiveData

    private val _cityWeatherLiveData by lazy {
        MutableLiveData<WeatherableDetails>()
    }

    val isLoadingLiveData: LiveData<Boolean>
        get() = _isLoadingLiveData

    private val _isLoadingLiveData by lazy {
        MutableLiveData<Boolean>()
    }

    val errorMessageLiveData: LiveData<String>
        get() = _errorMessageLiveData

    private val _errorMessageLiveData by lazy {
        MutableLiveData<String>()
    }

    init {
    }

    fun getWeather(city: String) {
//        viewModelScope.launch {
//            val weatherDeferred = repository.getWeather(city).await()
//            _cityWeatherLiveData.value = weatherDeferred
//        }

        viewModelScope.launch {
            repository.getWeather(city)
                .onStart {
                    _isLoadingLiveData.value = true
                }
                .onCompletion {
                    _isLoadingLiveData.value = false
                }
                //TODO: Create an extension to get the server errors.
                .catch { exception ->
                    Log.d(TAG, "Real exception $exception")
                    WeatherErrorResponder(exception).errorMessage?.let {
                        _errorMessageLiveData.value = it
                    } ?: run {
                        //TODO: Maybe send empty list here.
                    }

                    Log.e(TAG, "Error message ${exception.localizedMessage}")
                    if(exception is HttpException) {
                        val errorMessage = exception.response()?.errorBody()?.string() ?: ""
                        Log.d(TAG, "Exception response $errorMessage")
                    }
                }
                .collect {
                    _cityWeatherLiveData.value = it
                }

            repository.getAllCities()
                .collect {
                Log.d(TAG, "All cities count ${it.size}")
            }
        }
    }

    companion object {
        private val TAG = MainViewModel::class.simpleName
    }
}