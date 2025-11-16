package com.nat.greco.screens.receviceStock.di

import com.nat.greco.screens.receviceStock.data.ReceiveStockRepositoryImpl
import com.nat.greco.screens.receviceStock.domain.repository.ReceiveStockRepository
import com.nat.greco.screens.receviceStock.domain.usecases.ConfirmReceiveStockUseCase
import com.nat.greco.screens.receviceStock.domain.usecases.GetReceiveStockUseCase
import com.nat.greco.screens.receviceStock.presentation.ReceiveStockViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val ReceiveStockModule = module {
    single<ReceiveStockRepository> { ReceiveStockRepositoryImpl(get()) }
    single { GetReceiveStockUseCase(get()) }
    single { ConfirmReceiveStockUseCase(get()) }
    viewModel {
        ReceiveStockViewModel(
            get(), get(), get()
        )
    }
}