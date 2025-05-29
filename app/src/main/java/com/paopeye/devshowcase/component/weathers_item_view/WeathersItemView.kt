package com.paopeye.devshowcase.component.weathers_item_view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.paopeye.devshowcase.R
import com.paopeye.devshowcase.databinding.WeathersItemViewBinding
import com.paopeye.devshowcase.enums.DayTimePeriodType
import com.paopeye.domain.model.Weather
import com.paopeye.kit.util.date.DateParser

class WeathersItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private var binding: WeathersItemViewBinding

    init {
        val inflater = LayoutInflater.from(context)
        binding = WeathersItemViewBinding.inflate(inflater, this, true)
    }

    fun setupFromWeather(weather: Weather) {
        setCity(weather.cityName)
        setTime(DateParser.getCurrentTime())
        setTemp(weather.currentTemp.toString())
        setMinTemp(weather.minTemp.toString())
        setMaxTemp(weather.maxTemp.toString())
        setWeather(weather.weather)
        setBackground()
    }

    fun setCity(city: String) {
        binding.cityText.text = city
    }

    fun setTime(time: String) {
        binding.timeText.text = time
    }

    fun setTemp(temp: String) {
        binding.tempText.text = resources.getString(R.string.temp, temp)
    }

    fun setMaxTemp(maxTemp: String) {
        binding.maxTempText.text = resources.getString(R.string.maxTemp, maxTemp)
    }

    fun setMinTemp(minTemp: String) {
        binding.minTempText.text = resources.getString(R.string.minTemp, minTemp)
    }

    fun setWeather(weather: String) {
        binding.weatherText.text = weather
    }

    fun setBackground() {
        val currentTimeType = DayTimePeriodType.getType()
        binding.cardContentLayout.background = currentTimeType.getBackgroundColor()
    }
}
