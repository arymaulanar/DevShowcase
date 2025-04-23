package com.paopeye.devshowcase.di

import com.paopeye.devshowcase.ui.news.NewsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { NewsViewModel(get()) }
}
