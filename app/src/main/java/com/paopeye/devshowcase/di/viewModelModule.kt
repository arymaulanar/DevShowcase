package com.paopeye.devshowcase.di

import com.paopeye.devshowcase.ui.news.NewsViewModel
import com.paopeye.devshowcase.ui.news_detail.NewsDetailViewModel
import com.paopeye.devshowcase.ui.weather.WeatherViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { NewsViewModel(get()) }
    viewModel { NewsDetailViewModel() }
    viewModel { WeatherViewModel(get(), get()) }
}
