package com.paopeye.devshowcase.component.temperature_view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.paopeye.devshowcase.R
import com.paopeye.devshowcase.databinding.TemperatureViewBinding
import com.paopeye.devshowcase.enums.WeatherIconType
import com.paopeye.domain.model.Weather
import com.paopeye.kit.extension.emptyInt
import com.paopeye.kit.util.date.DateParser

class TemperatureView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private var binding: TemperatureViewBinding

    init {
        val inflater = LayoutInflater.from(context)
        binding = TemperatureViewBinding.inflate(inflater, this, true)
    }

    fun setupFromWeather(weather: Weather, isNow: Boolean) {
        var timeText = weather.getHour()
        if (isNow) timeText = resources.getString(R.string.now)
        binding.tempText.text = resources.getString(R.string.temp, weather.currentTemp.toString())
        binding.timeText.text = timeText
        val icon = WeatherIconType.getType(weather.weather).getIcon()
        if (icon == emptyInt()) return
        binding.iconImage.setImageResource(icon)
    }
}
