package com.nat.greco.screens.orders.di

import com.nat.greco.screens.orders.data.OrdersRepositoryImpl
import com.nat.greco.screens.orders.domain.repository.OrdersRepository
import com.nat.greco.screens.orders.domain.usecases.GetOrdersListUseCase
import com.nat.greco.screens.orders.domain.usecases.GetReturnsListUseCase
import com.nat.greco.screens.orders.presentation.OrdersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val OrdersModule = module {
    single<OrdersRepository> { OrdersRepositoryImpl(get()) }
    single { GetOrdersListUseCase(get()) }
    single { GetReturnsListUseCase(get()) }
    viewModel {
        OrdersViewModel(get(), get(), get())
    }
}