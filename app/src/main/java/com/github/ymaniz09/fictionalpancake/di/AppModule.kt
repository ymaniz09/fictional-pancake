package com.github.ymaniz09.fictionalpancake.di

import com.github.ymaniz09.fictionalpancake.country.ui.viewmodel.CountryViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { CountryViewModel() }
}