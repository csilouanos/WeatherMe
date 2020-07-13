package com.example.weatherme.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.weatherme.databinding.FragmentSearchCityBinding
import com.example.weatherme.extensions.onTextChanged
import com.example.weatherme.mvvm.view_models.MainViewModel
import com.example.weatherme.ui.adapters.SearchCitiesAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

@ExperimentalCoroutinesApi
class SearchCityFragment: ViewBindingFragment<FragmentSearchCityBinding>() {

    private val viewModel: MainViewModel by sharedViewModel()
    private lateinit var adapter: SearchCitiesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchCityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureElements()
        configureObservers()
    }

    private fun configureObservers() {
        viewModel.liveCityWeatherLiveData.observe(viewLifecycleOwner, Observer { cities ->
            adapter.refreshData(ArrayList(cities))
        })

        viewModel.isLoadingLiveData.observe(viewLifecycleOwner, Observer { isLoading ->
            binding.swipeRefreshLayout.isRefreshing = isLoading
        })
    }

    private fun configureElements() {
        binding.run {
            searchTextInputLayout.setStartIconOnClickListener {
                viewModel.dismissSearchForCityView()
            }

            searchEditText.onTextChanged { text ->
                viewModel.searchCityWeather(text)
            }
            adapter = SearchCitiesAdapter { selectedItem ->
                viewModel.saveCity(selectedItem)
            }.also {
                recyclerView.adapter = it
            }

            swipeRefreshLayout.setOnRefreshListener {
                val text = searchEditText.text?.toString() ?: run {
                    swipeRefreshLayout.isRefreshing = false
                    return@setOnRefreshListener
                }
                viewModel.searchCityWeather(text)
            }
        }
    }

    companion object {
        private val TAG = SearchCityFragment::class.simpleName
    }


}