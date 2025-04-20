package com.paopeye.devshowcase.di

import com.paopeye.devshowcase.MainActivityViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainActivityViewModel(get()) }
}
