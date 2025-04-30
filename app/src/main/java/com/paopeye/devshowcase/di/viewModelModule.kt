package com.paopeye.devshowcase.di

import com.paopeye.devshowcase.ui.news.NewsViewModel
import com.paopeye.devshowcase.ui.news_detail.NewsDetailViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { NewsViewModel(get()) }
    viewModel { NewsDetailViewModel() }
}
