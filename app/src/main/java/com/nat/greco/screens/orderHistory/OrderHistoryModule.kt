package com.nat.greco.screens.orderHistory

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val orderHistoryModule = module {
    viewModel {
        OrderHistoryViewModel(get(), get())
    }
}