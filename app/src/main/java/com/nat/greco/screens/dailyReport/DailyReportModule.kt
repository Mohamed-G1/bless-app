package com.nat.greco.screens.dailyReport

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val DailyReportModule = module {
    viewModel{
        DailyReportViewModel(
            getDayDetailsUseCase = get(),
            getUserDataManager = get()
        )
    }
}