package com.example.weatherme.ui.view_holders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherme.R
import com.example.weatherme.models.Weatherable

class SearchCityViewHolder(parent: ViewGroup) :
    RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_holder_search_city, parent, false)) {

    private var temperatureTextView: AppCompatTextView = itemView.findViewById(R.id.temperatureTextView)
    private var cityTextView: AppCompatTextView = itemView.findViewById(R.id.cityTextView)

    var data: Weatherable? = null
        set(value) {
            field = value
            value?.let {
                cityTextView.text = it.titleName
                temperatureTextView.text = it.temperature
            }
        }
}