package com.nat.bless.screens.salespersonScreen

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val SalespersonModule = module {
    viewModel {
        SalespersonViewModel(get(), get())
    }
}