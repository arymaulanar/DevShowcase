package com.paopeye.devshowcase.di

import com.paopeye.devshowcase.ui.news.NewsViewModel
import com.paopeye.devshowcase.ui.news_detail.NewsDetailViewModel
import com.paopeye.devshowcase.ui.profile.contact.ProfileContactViewModel
import com.paopeye.devshowcase.ui.profile.credit.ProfileCreditViewModel
import com.paopeye.devshowcase.ui.weather.WeatherViewModel
import com.paopeye.devshowcase.ui.weather_detail.WeatherDetailViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { NewsViewModel(get()) }
    viewModel { NewsDetailViewModel() }
    viewModel { WeatherViewModel(get(), get(), get()) }
    viewModel { WeatherDetailViewModel(get()) }
    viewModel { ProfileCreditViewModel() }
    viewModel { ProfileContactViewModel() }
}
