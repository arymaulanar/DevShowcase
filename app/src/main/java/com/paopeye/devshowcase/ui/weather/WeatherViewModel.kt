package com.paopeye.devshowcase.ui.weather

import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.paopeye.devshowcase.base.BaseViewModel
import com.paopeye.devshowcase.util.StateWrapper
import com.paopeye.domain.model.Weather
import com.paopeye.domain.usecase.weather.GetCachedWeathersUseCase
import com.paopeye.kit.util.date.DateParser

class WeatherViewModel(
    private val getWeathersCachedWeathersUseCase: GetCachedWeathersUseCase
) : BaseViewModel() {
    private val _state = MutableLiveData<StateWrapper<State>>()
    val state: LiveData<StateWrapper<State>> = _state

    sealed class Event {
        data object OnCreate : Event()
        data class OnGetLocation(val location: Location) : Event()
        data class OnQuerySearchBar(val query: String) : Event()
    }

    sealed class State {
        data class ShowWeathers(val weathers: List<Weather>) : State()
        data object ShowEmptyWeathers : State()
        data object ShowLoading : State()
        data object HideLoading : State()
    }

    private var currentLocation: Location? = null

    fun onEvent(event: Event) {
        when (event) {
            Event.OnCreate -> onCreate()
            is Event.OnQuerySearchBar -> onQuerySearchBar(event.query)
            is Event.OnGetLocation -> onGetLocation(event.location)
        }
    }

    private fun onCreate() {
        onGetSavedWeathers()
    }

    private fun onQuerySearchBar(query: String) {
        Log.d("TAG", "onQuerySearchBar: $query")
    }

    private fun onGetSavedWeathers() {
        val currentTimestamp = DateParser.getTimestampInSecond()
        val savedWeathers = getWeathersCachedWeathersUseCase.invoke()
        val weathersGroupBy = savedWeathers.weathers.groupBy { it.cityName }.values
        val weathersFiltered = weathersGroupBy.mapNotNull { value ->
            val filtered = value.filter { it.timestamp <= currentTimestamp }
            if (filtered.isEmpty()) return@mapNotNull null
            filtered.maxByOrNull { it.timestamp }
        }
        if (weathersFiltered.isEmpty()) return setState(State.ShowEmptyWeathers)
        setState(State.ShowWeathers(weathersFiltered))
    }

    private fun onGetLocation(location: Location) {
        if (currentLocation != null) return
        Log.d("TAG", "onGetLocation: $location")
        currentLocation = location
    }

    private fun setState(state: State) {
        _state.value = StateWrapper(state)
    }
}

