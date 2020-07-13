package com.example.weatherme.ui.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherme.models.Weatherable
import com.example.weatherme.ui.view_holders.SearchCityViewHolder

class SearchCitiesAdapter(val onItemSelected: (item: Weatherable) -> Unit) : RecyclerView.Adapter<SearchCityViewHolder>() {

    private var weatherables = ArrayList<Weatherable>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchCityViewHolder {
        return SearchCityViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return weatherables.size
    }

    override fun onBindViewHolder(holder: SearchCityViewHolder, position: Int) {
        holder.data = weatherables[position]
        holder.itemView.setOnClickListener {
            onItemSelected(weatherables[position])
        }
    }

    fun refreshData(weatherables: List<Weatherable>) {
        this.weatherables.clear()
        this.weatherables.addAll(weatherables)
        notifyDataSetChanged()
    }
}