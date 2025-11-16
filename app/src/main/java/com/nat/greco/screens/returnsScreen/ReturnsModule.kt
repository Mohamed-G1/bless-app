package com.nat.greco.screens.returnsScreen

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val ReturnsModule = module {
    single<ReturnsRepository> { ReturnsRepositoryImpl(get()) }
    single { SendReturnsUseCase(get()) }
    viewModel {
        ReturnsViewModel(get(), get(), get())
    }
}