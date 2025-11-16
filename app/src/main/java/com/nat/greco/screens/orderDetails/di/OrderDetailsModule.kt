package com.nat.greco.screens.orderDetails.di

import com.nat.greco.screens.confirmOrder.ConfirmOrderViewModel
import com.nat.greco.screens.orderDetails.data.OrderDetailsRepositoryImpl
import com.nat.greco.screens.orderDetails.domain.repository.OrderDetailsRepository
import com.nat.greco.screens.orderDetails.domain.usecases.ConfirmOrderUseCase
import com.nat.greco.screens.orderDetails.domain.usecases.GetOrderDetailsUseCase
import com.nat.greco.screens.orderDetails.presentation.OrderDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val OrderDetailsModule = module {
    single<OrderDetailsRepository> { OrderDetailsRepositoryImpl(get()) }
    single { GetOrderDetailsUseCase(get()) }
    single { ConfirmOrderUseCase(get()) }
    viewModel {
        OrderDetailsViewModel(get(), get())
    }

    viewModel {
        ConfirmOrderViewModel(get(), get(), get())
    }
}