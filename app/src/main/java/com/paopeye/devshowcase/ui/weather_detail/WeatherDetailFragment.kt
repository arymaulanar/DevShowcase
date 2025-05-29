package com.paopeye.devshowcase.ui.weather_detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.bundle.bundleOf
import androidx.core.view.isVisible
import com.paopeye.devshowcase.R
import com.paopeye.devshowcase.base.BaseFragment
import com.paopeye.devshowcase.databinding.FragmentWeatherDetailBinding
import com.paopeye.devshowcase.enums.DayTimePeriodType
import com.paopeye.devshowcase.util.setFullScreenMarginTop
import com.paopeye.devshowcase.util.subscribeSingleState
import com.paopeye.domain.model.Weather
import com.paopeye.kit.extension.orEmpty
import com.paopeye.kit.extension.orFalse
import com.paopeye.kit.extension.parcelableArrayList
import org.koin.androidx.viewmodel.ext.android.viewModel

class WeatherDetailFragment : BaseFragment<FragmentWeatherDetailBinding>() {
    private val viewModel: WeatherDetailViewModel by viewModel()
    override fun isUseToolbar() = false
    override fun isUseLeftImageToolbar() = false
    override fun isFullScreen() = true
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentWeatherDetailBinding {
        return FragmentWeatherDetailBinding.inflate(inflater, container, false)
    }

    private val weathers by lazy {
        arguments?.parcelableArrayList(WEATHERS, Weather::class.java).orEmpty()
    }
    private val isNew by lazy {
        arguments?.getBoolean(IS_NEW).orFalse()
    }

    override fun setupView() {
        binding.addTextButton.isVisible = isNew
        binding.customToolbarView.setFullScreenMarginTop()
        binding.backButton.setOnClickListener { navigateBackToList() }
        binding.addTextButton.setOnClickListener {
            viewModel.onEvent(WeatherDetailViewModel.Event.OnAddCurrentWeathers)
        }
        viewModel.onEvent(WeatherDetailViewModel.Event.OnCreate(weathers))
    }

    override fun subscribeState() {
        subscribeSingleState(viewModel.state) {
            when (it) {
                WeatherDetailViewModel.State.HideLoading -> hideLoading()
                WeatherDetailViewModel.State.ShowLoading -> showLoading()
                WeatherDetailViewModel.State.NavigateBackToList -> navigateBackToList()
                is WeatherDetailViewModel.State.ShowBackground -> showBackground(it.dayTimePeriodType)
                is WeatherDetailViewModel.State.ShowCity -> showCity(it.city)
                is WeatherDetailViewModel.State.ShowTemperature -> showTemperature(it.temperature)
                is WeatherDetailViewModel.State.ShowWeather -> showWeather(it.weather)
                is WeatherDetailViewModel.State.ShowForecast -> showForecast(it.weathers)
                is WeatherDetailViewModel.State.ShowHumidity -> showHumidity(it.humidity)
                is WeatherDetailViewModel.State.ShowFeelLike -> showFeelLike(it.feelLike)
                is WeatherDetailViewModel.State.ShowWind -> showWind(it.weather)
                is WeatherDetailViewModel.State.ShowTemperatureIndex -> showTemperatureIndex(it.weather)
            }
        }
    }

    private fun navigateBackToList() {
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }

    private fun showTemperatureIndex(weather: Weather) {
        binding.minMaxTempView.setTemperatures(
            weather.minTemp,
            weather.maxTemp,
            weather.currentTemp
        )
    }

    private fun showWind(weather: Weather) {
        binding.windForecastView.setupView(weather.windSpeed, weather.windDegrees)
    }

    private fun showFeelLike(feelLike: Int) {
        binding.feelLikeText.text =
            String.format(feelLike.toString()).plus(getString(R.string.degree))
    }

    private fun showHumidity(humidity: Int) {
        binding.humidityText.text =
            String.format(humidity.toString()).plus(getString(R.string.percent))
    }

    private fun showForecast(weathers: List<Weather>) {
        binding.forecastView.setupView(weathers)
    }

    private fun showWeather(weather: String) {
        binding.weatherText.text = weather
    }

    private fun showTemperature(temperature: String) {
        binding.temperatureText.text = temperature
    }

    private fun showCity(city: String) {
        binding.cityText.text = city
    }

    private fun showBackground(dayTimePeriodType: DayTimePeriodType) {
        binding.root.background = dayTimePeriodType.getBackgroundColor()
    }

    companion object {
        const val WEATHERS = ".weathers"
        const val IS_NEW = ".isNew"
        fun newInstance(
            weathers: List<Weather>,
            isNew: Boolean = false,
        ): WeatherDetailFragment {
            val fragment = WeatherDetailFragment()
            fragment.arguments = bundleOf(
                WEATHERS to weathers,
                IS_NEW to isNew
            )
            return fragment
        }
    }

}
