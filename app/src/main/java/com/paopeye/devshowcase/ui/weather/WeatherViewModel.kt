package com.paopeye.devshowcase.ui.weather

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.paopeye.devshowcase.base.BaseViewModel
import com.paopeye.devshowcase.util.StateWrapper
import com.paopeye.domain.datastate.DataState
import com.paopeye.domain.model.CityAutoComplete
import com.paopeye.domain.model.Weather
import com.paopeye.domain.usecase.photon.GetCityAutoCompletesUseCase
import com.paopeye.domain.usecase.weather.GetCachedWeathersUseCase
import com.paopeye.kit.extension.emptyString
import com.paopeye.kit.extension.orEmpty
import com.paopeye.kit.util.date.DateParser
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val getWeathersCachedWeathersUseCase: GetCachedWeathersUseCase,
    private val getCityAutoCompletesUseCase: GetCityAutoCompletesUseCase
) : BaseViewModel() {
    private val _state = MutableLiveData<StateWrapper<State>>()
    val state: LiveData<StateWrapper<State>> = _state
    private var query = emptyString()

    sealed class Event {
        data object OnCreate : Event()
        data class OnGetLocation(val location: Location) : Event()
        data class OnQuerySearchBar(val query: String) : Event()
        data class OnSelectedAutoComplete(val city: CityAutoComplete) : Event()
    }

    sealed class State {
        data class ShowWeathers(val weathers: List<Weather>) : State()
        data class ShowCityAutoCompletes(val cities: List<CityAutoComplete>) : State()
        data class ShowLoadingSearchBar(val isLoading: Boolean) : State()
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
            is Event.OnSelectedAutoComplete -> onSelectedAutoComplete(event.city)
        }
    }

    private fun onCreate() {
        onGetSavedWeathers()
    }

    private fun onQuerySearchBar(newQuery: String) {
        if (newQuery.isEmpty() || newQuery.isBlank()) return setState(
            State.ShowCityAutoCompletes(
                emptyList()
            )
        )
        if (query == newQuery) return
        this.query = newQuery
        onGetCityAutoCompletes()
    }

    private fun onGetCityAutoCompletes() = launch {
        val request = CityAutoComplete(
            query = query,
            latitude = currentLocation?.latitude.orEmpty().toString(),
            longitude = currentLocation?.longitude.orEmpty().toString()
        )
        setState(State.ShowLoadingSearchBar(true))
        val result = getCityAutoCompletesUseCase.invoke(request)
        setState(State.ShowLoadingSearchBar(false))
        if (result is DataState.ERROR) return@launch
        val cities = result.data?.cities.orEmpty()
        val filteredCities = cities.distinctBy { it.city }
        setState(State.ShowCityAutoCompletes(filteredCities))
    }

    private fun onGetSavedWeathers() {
        setState(State.ShowLoading)
        val currentTimestamp = DateParser.getTimestampInSecond()
        val savedWeathers = getWeathersCachedWeathersUseCase.invoke()
        val weathersGroupBy = savedWeathers.weathers.groupBy { it.cityName }.values
        val weathersFiltered = weathersGroupBy.mapNotNull { value ->
            val filtered = value.filter { it.timestamp <= currentTimestamp }
            if (filtered.isEmpty()) return@mapNotNull null
            filtered.maxByOrNull { it.timestamp }
        }
        setState(State.HideLoading)
        if (weathersFiltered.isEmpty()) return setState(State.ShowEmptyWeathers)
        setState(State.ShowWeathers(weathersFiltered))
    }

    private fun onGetLocation(location: Location) {
        if (currentLocation != null) return
        currentLocation = location
    }

    private fun onSelectedAutoComplete(city: CityAutoComplete) {

    }

    private fun setState(state: State) {
        _state.value = StateWrapper(state)
    }
}

