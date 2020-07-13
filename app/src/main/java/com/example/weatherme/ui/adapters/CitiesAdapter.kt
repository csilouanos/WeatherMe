package com.example.weatherme.ui.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherme.models.WeatherableDetails
import com.example.weatherme.ui.view_holders.CityViewHolder

class CitiesAdapter : RecyclerView.Adapter<CityViewHolder>() {

    private var weatherables = ArrayList<WeatherableDetails>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        return CityViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return weatherables.size
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.data = weatherables[position]
    }

    fun refreshData(weatherables: List<WeatherableDetails>) {
        this.weatherables.clear()
        this.weatherables.addAll(weatherables)
        notifyDataSetChanged()
    }
}