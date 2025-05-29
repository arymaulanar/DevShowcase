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
import com.paopeye.domain.usecase.weather.GetWeatherForecastsUseCase
import com.paopeye.kit.extension.emptyString
import com.paopeye.kit.extension.orEmpty
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val getWeathersCachedWeathersUseCase: GetCachedWeathersUseCase,
    private val getWeatherForecastsUseCase: GetWeatherForecastsUseCase,
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
        data class OnSavedWeatherClicked(val weather: Weather) : Event()
    }

    sealed class State {
        data class ShowWeathers(val weathers: List<Weather>) : State()
        data class ShowCityAutoCompletes(val cities: List<CityAutoComplete>) : State()
        data class ShowLoadingSearchBar(val isLoading: Boolean) : State()
        data class NavigateToDetail(val weathers: List<Weather>, val isNew: Boolean) : State()
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
            is Event.OnSavedWeatherClicked -> onSavedWeatherClicked(event.weather)
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

    private fun onSavedWeatherClicked(weather: Weather) {
        getWeatherForecast(weather)
    }

    private fun onGetSavedWeathers() = launch {
        setState(State.ShowLoading)
        val savedWeathers = getWeathersCachedWeathersUseCase.invoke()
        val weathers = savedWeathers.weathers.distinctBy { it.cityName }
        setState(State.HideLoading)
        if (weathers.isEmpty()) return@launch setState(State.ShowEmptyWeathers)
        setState(State.ShowWeathers(weathers))
    }

    private fun onGetLocation(location: Location) {
        if (currentLocation != null) return
        currentLocation = location
    }

    private fun onSelectedAutoComplete(city: CityAutoComplete) {
        val request = Weather(
            latitude = city.latitude,
            longitude = city.longitude,
            cityName = city.city
        )
        getWeatherForecast(request, true)
    }

    private fun getWeatherForecast(request: Weather, isNew: Boolean = false) = launch {
        setState(State.ShowLoading)
        val result = getWeatherForecastsUseCase.invoke(request)
        setState(State.HideLoading)
        if (result is DataState.ERROR) return@launch
        val weathers = result.data?.weathers?.map { it.copy(cityName = request.cityName) }.orEmpty()
        setState(State.NavigateToDetail(weathers, isNew))
    }

    private fun setState(state: State) {
        _state.value = StateWrapper(state)
    }
}

