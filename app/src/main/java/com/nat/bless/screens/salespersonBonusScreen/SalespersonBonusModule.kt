package com.nat.bless.screens.salespersonBonusScreen

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val SalespersonBonus = module {
    single<SalespersonBonusRepository> { SalespersonBonusRepositoryImpl(get()) }
    viewModel {
        SalespersonBonusViewModel(
            getUserDataManager = get(),
            repository = get()
        )
    }
}