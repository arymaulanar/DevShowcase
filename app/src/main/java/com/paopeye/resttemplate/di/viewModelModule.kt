package com.paopeye.resttemplate.di

import com.paopeye.resttemplate.MainActivityViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainActivityViewModel(get()) }
}
