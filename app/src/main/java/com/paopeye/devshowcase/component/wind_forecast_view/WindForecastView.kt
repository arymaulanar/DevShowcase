package com.paopeye.devshowcase.component.wind_forecast_view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.paopeye.devshowcase.databinding.WindForecastViewBinding
import java.math.BigDecimal

class WindForecastView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var binding: WindForecastViewBinding

    init {
        val inflater = LayoutInflater.from(context)
        binding = WindForecastViewBinding.inflate(inflater, this, true)
    }

    fun setupView(windSpeed: BigDecimal, windDirection: Int) {
        binding.windSpeedText.text = String.format(windSpeed.toString())
        binding.arrowWindImage.rotation = windDirection.toFloat()
    }

}
