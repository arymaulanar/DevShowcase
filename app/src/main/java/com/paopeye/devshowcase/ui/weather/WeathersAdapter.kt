package com.paopeye.devshowcase.ui.weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.paopeye.devshowcase.databinding.ItemWeatherBinding
import com.paopeye.domain.model.Weather
import com.paopeye.kit.extension.emptyInt

class WeathersAdapter : RecyclerView.Adapter<WeathersAdapter.WeathersViewHolder>() {
    private var onClickListener: ((Weather) -> Unit)? = null
    var weathers = listOf<Weather>()
        set(value) {
            field = value
            notifyItemRangeChanged(emptyInt(), weathers.size)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeathersViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemWeatherBinding.inflate(layoutInflater, parent, false)
        return WeathersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeathersViewHolder, position: Int) {
        holder.bind(weathers[position]) { onClickListener?.invoke(it) }
    }

    override fun getItemCount(): Int = weathers.size

    fun setOnClickListener(onClick: (Weather) -> Unit) {
        onClickListener = onClick
    }

    inner class WeathersViewHolder(private val binding: ItemWeatherBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            data: Weather,
            onClick: (Weather) -> Unit
        ) {
            binding.weathersItemView.setupFromWeather(data)
            binding.root.setOnClickListener {
                onClick.invoke(data)
            }
        }
    }
}
