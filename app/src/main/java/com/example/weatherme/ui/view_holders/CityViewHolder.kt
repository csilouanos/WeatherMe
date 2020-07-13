package com.example.weatherme.ui.view_holders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherme.R
import com.example.weatherme.extensions.load
import com.example.weatherme.models.WeatherableDetails
import com.google.android.material.textview.MaterialTextView

class CityViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(
        R.layout.view_holder_city, parent, false)) {

    private var weatherImageView: AppCompatImageView = itemView.findViewById(R.id.weatherImageView)
    private var temperatureTextView: MaterialTextView = itemView.findViewById(R.id.temperatureTextView)
    private var cityTextView: MaterialTextView = itemView.findViewById(R.id.cityTextView)
    private var countryTextView: MaterialTextView = itemView.findViewById(R.id.countryTextView)

    var data: WeatherableDetails? = null
        set(value) {
            field = value
            value?.let {
                cityTextView.text = it.titleName
                temperatureTextView.text = it.temperature
                weatherImageView.load(it.icon)
                countryTextView.text = it.subtitleCountry
            }
        }
}