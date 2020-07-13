package com.example.weatherme.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.weatherme.mvvm.view_models.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class MainActivity: AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //TODO: Configure layout.

        configureObservers()
    }

    private fun configureObservers() {
        viewModel.cityWeatherLiveData.observe(this, Observer {
            Log.d(TAG, "weather data ${it.temperature}")
        })

        viewModel.isLoadingLiveData.observe(this, Observer {
            Log.d(TAG, "is loading $it")
        })

        viewModel.errorMessageLiveData.observe(this, Observer { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        })
    }

    companion object {
        private val TAG = MainActivity::class.simpleName
    }
}