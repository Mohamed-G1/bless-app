package com.nat.bless.screens.home.di

import com.nat.bless.screens.home.data.repository.HomeRepositoryImpl
import com.nat.bless.screens.home.domain.repository.HomeRepository
import com.nat.bless.screens.home.domain.usecases.GetRoutesUseCase
import com.nat.bless.screens.home.domain.usecases.SaveRouteIdUseCase
import com.nat.bless.screens.home.domain.usecases.SendLocationUseCase
import com.nat.bless.screens.home.presentation.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    single<HomeRepository> {
        HomeRepositoryImpl(get())
    }

    single {
        GetRoutesUseCase(get())
    }

    single { SendLocationUseCase(get()) }
    single { SaveRouteIdUseCase(get()) }


    viewModel {
        HomeViewModel(get(), get(), get(), get(), get(), get())
    }
}