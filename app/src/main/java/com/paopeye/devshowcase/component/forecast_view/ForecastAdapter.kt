package com.paopeye.devshowcase.component.forecast_view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.paopeye.devshowcase.databinding.ItemForecastBinding
import com.paopeye.domain.model.Weather
import com.paopeye.kit.extension.emptyInt

class ForecastAdapter : RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {
    var weathers = listOf<Weather>()
        set(value) {
            field = value
            notifyItemRangeChanged(emptyInt(), weathers.size)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemForecastBinding.inflate(layoutInflater, parent, false)
        return ForecastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        holder.bind(weathers[position], position)
    }

    override fun getItemCount(): Int = weathers.size

    inner class ForecastViewHolder(private val binding: ItemForecastBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Weather, position: Int) {
            binding.temperatureView.setupFromWeather(data, position == emptyInt())
        }
    }
}
