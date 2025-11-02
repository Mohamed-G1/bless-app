package com.nat.greco.screens.dayDetails.di

import com.nat.greco.screens.dayDetails.data.DayDetailsRepositoryImpl
import com.nat.greco.screens.dayDetails.domain.data.DayDetailsRepository
import com.nat.greco.screens.dayDetails.domain.usecases.EndDayUseCase
import com.nat.greco.screens.dayDetails.domain.usecases.GetDayDetailsUseCase
import com.nat.greco.screens.dayDetails.presentation.DayDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val DayDetailsModule = module {
    single<DayDetailsRepository> { DayDetailsRepositoryImpl(get()) }
    single { GetDayDetailsUseCase(get()) }
    single { EndDayUseCase(get()) }
    viewModel {
        DayDetailsViewModel(get(), get(), get())
    }
}