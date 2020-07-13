package com.example.weatherme.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.lifecycle.Observer
import com.example.weatherme.R
import com.example.weatherme.databinding.FragmentSavedCitiesBinding
import com.example.weatherme.mvvm.view_models.MainViewModel
import com.example.weatherme.ui.adapters.CitiesAdapter
import com.example.weatherme.ui.adapters.SearchCitiesAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

@ExperimentalCoroutinesApi
class SavedCitiesFragment : ViewBindingFragment<FragmentSavedCitiesBinding>() {

    private val viewModel: MainViewModel by sharedViewModel()
    private lateinit var adapter: CitiesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSavedCitiesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureElements()
        configureObservers()
        viewModel.getLatestSavedCities()
    }

    private fun configureObservers() {
        viewModel.savedCitiesWeatherLiveData.observe(viewLifecycleOwner, Observer { savedCities ->
            adapter.refreshData(savedCities)
            Log.d(TAG, "Saved cities")
            savedCities.forEach {
                Log.d(TAG, "city ${it.titleName}")
            }
        })
    }

    private fun configureElements() {
        setHasOptionsMenu(true)
        binding.run {
            toolbar.setOnMenuItemClickListener{menuItem ->
                when(menuItem.itemId) {
                    R.id.search -> {
                        viewModel.showSearchForCityView()
                        true
                    }
                    else -> {
                        false
                    }
                }
            }
            adapter = CitiesAdapter().also {
                recyclerView.adapter = it
            }

        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    companion object {
        private val TAG = SavedCitiesFragment::class.simpleName
    }

}