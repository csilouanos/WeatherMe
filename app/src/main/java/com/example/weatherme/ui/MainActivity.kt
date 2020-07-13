package com.example.weatherme.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.weatherme.databinding.ActivityMainBinding
import com.example.weatherme.extensions.replace
import com.example.weatherme.mvvm.view_models.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class MainActivity: AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configureBinding()
        loadInitialFragment()
        configureObservers()
    }

    private fun loadInitialFragment() {
        supportFragmentManager.replace(SavedCitiesFragment())
    }

    private fun configureBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun configureObservers() {

        viewModel.errorMessageLiveData.observe(this, Observer { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        })

        viewModel.shouldShowSearchForCityLiveData.observe(this, Observer { shouldSearch ->
            if(shouldSearch) {
                supportFragmentManager.replace(SearchCityFragment())
            } else {
                supportFragmentManager.popBackStack()
            }
        })
    }

    companion object {
        private val TAG = MainActivity::class.simpleName
    }
}