package com.nat.greco.screens.home.di

import com.nat.greco.screens.home.data.repository.HomeRepositoryImpl
import com.nat.greco.screens.home.domain.repository.HomeRepository
import com.nat.greco.screens.home.domain.usecases.GetCouriersUseCase
import com.nat.greco.screens.home.domain.usecases.SendLocationUseCase
import com.nat.greco.screens.home.presentation.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    single<HomeRepository> {
        HomeRepositoryImpl(get())
    }

    single {
        GetCouriersUseCase(get())
    }

    single { SendLocationUseCase(get()) }


    viewModel {
        HomeViewModel(get(), get(), get(), get())
    }
}