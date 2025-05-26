package com.paopeye.devshowcase.ui.weather_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.paopeye.devshowcase.base.BaseViewModel
import com.paopeye.devshowcase.enums.DayTimePeriodType
import com.paopeye.devshowcase.util.StateWrapper
import com.paopeye.domain.model.Weather
import com.paopeye.kit.util.date.DateParser
import kotlinx.coroutines.launch

class WeatherDetailViewModel : BaseViewModel() {
    private val _state = MutableLiveData<StateWrapper<State>>()
    val state: LiveData<StateWrapper<State>> = _state

    sealed class Event {
        data class OnCreate(val weathers: List<Weather>) : Event()
    }

    sealed class State {
        data class ShowBackground(val dayTimePeriodType: DayTimePeriodType) : State()
        data class ShowCity(val city: String) : State()
        data class ShowTemperature(val temperature: String) : State()
        data class ShowWeather(val weather: String) : State()
        data class ShowForecast(val weathers: List<Weather>) : State()
        data class ShowHumidity(val humidity: Int) : State()
        data class ShowTemperatureIndex(val weather: Weather) : State()
        data class ShowFeelLike(val feelLike: Int) : State()
        data class ShowWind(val weather: Weather) : State()
        data object ShowLoading : State()
        data object HideLoading : State()
    }

    fun onEvent(event: Event) {
        when (event) {
            is Event.OnCreate -> onCreate(event.weathers)
        }
    }

    private fun onCreate(weathers: List<Weather>) = launch {
        val getCurrentTime = DateParser.getTimestampInSecond()
        val filteredWeathers = filterWeathersByCurrentTime(weathers, getCurrentTime)
        val currentWeather = filteredWeathers.firstOrNull() ?: return@launch
        setState(State.ShowBackground(DayTimePeriodType.getType(getCurrentTime)))
        setState(State.ShowCity(currentWeather.cityName))
        setState(State.ShowTemperature(currentWeather.currentTemp.toString()))
        setState(State.ShowWeather(currentWeather.weather))
        setState(State.ShowForecast(filteredWeathers))
        setState(State.ShowFeelLike(currentWeather.feelsLikeTemp))
        setState(State.ShowHumidity(currentWeather.humidity))
        setState(State.ShowTemperatureIndex(currentWeather))
        setState(State.ShowWind(currentWeather))
    }

    private fun filterWeathersByCurrentTime(
        weathers: List<Weather>,
        currentTime: Long
    ): List<Weather> {
        val filteredData = weathers.filter { it.timestamp >= currentTime }
        return filteredData
    }

    private fun setState(state: State) {
        _state.value = StateWrapper(state)
    }
}
