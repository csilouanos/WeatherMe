package com.example.weatherme.mvvm.view_models

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.weatherme.database.Weather
import com.example.weatherme.models.WeatherErrorResponder
import com.example.weatherme.models.Weatherable
import com.example.weatherme.models.WeatherableDetails
import com.example.weatherme.mvvm.repositories.WeatherRepository
import com.example.weatherme.vendor.SingleLiveEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.HttpException

@ExperimentalCoroutinesApi
class MainViewModel(application: Application, private val repository: WeatherRepository) :
    AndroidViewModel(application) {

    val liveCityWeatherLiveData: LiveData<List<Weatherable>>
        get() = _liveCityWeatherLiveData

    private val _liveCityWeatherLiveData by lazy {
        SingleLiveEvent<List<Weatherable>>()
    }

    val savedCitiesWeatherLiveData: LiveData<List<WeatherableDetails>>
        get() = _savedCitiesWeatherLiveData

    private val _savedCitiesWeatherLiveData by lazy {
        MutableLiveData<List<WeatherableDetails>>()
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

    val shouldShowSearchForCityLiveData: LiveData<Boolean>
        get() = _shouldShowSearchForCityLiveData

    private val _shouldShowSearchForCityLiveData by lazy {
        SingleLiveEvent<Boolean>()
    }

    init {
    }

    fun searchCityWeather(city: String) {
        viewModelScope.launch {
            Log.d(TAG, "Execute launch")
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
                    if (exception is HttpException) {
                        val errorMessage = exception.response()?.errorBody()?.string() ?: ""
                        Log.d(TAG, "Exception response $errorMessage")
                    }
                }
                .collect {
                    repository.insertWeatherEntry(it)

                    repository.findNonSavedCitiesFromDB(city)
                        .collect { cities ->
                            cities.forEach {
                                Log.d(TAG, "Non saved city ${it.name} ${it.isSaved}")
                            }
                            _liveCityWeatherLiveData.value = cities
                        }
                }

        }
    }

    fun getLatestSavedCities() {
        viewModelScope.launch {
            repository.findSavedCitiesFromDB()
                .collect {
                    _savedCitiesWeatherLiveData.value = it
                }
        }
    }

    fun saveCity(city: Weatherable) {
        viewModelScope.launch {
            val weather = city as Weather
            repository.markEntryAsSaved(weather)
            dismissSearchForCityView()
        }
    }

    fun showSearchForCityView() {
        _shouldShowSearchForCityLiveData.value = true
    }

    fun dismissSearchForCityView() {
        val isSearchForCityVisible = _shouldShowSearchForCityLiveData.value ?: false
        if (isSearchForCityVisible) {
            _shouldShowSearchForCityLiveData.value = false
        }
    }

    companion object {
        private val TAG = MainViewModel::class.simpleName
    }
}