package com.example.myrealtripchallenge.di

import com.example.myrealtripchallenge.viewModel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel{
        MainViewModel(get(), get())
    }
}