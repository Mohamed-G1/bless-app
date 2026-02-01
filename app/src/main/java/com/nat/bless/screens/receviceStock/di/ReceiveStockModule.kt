package com.nat.bless.screens.receviceStock.di

import com.nat.bless.screens.receviceStock.data.ReceiveStockRepositoryImpl
import com.nat.bless.screens.receviceStock.domain.repository.ReceiveStockRepository
import com.nat.bless.screens.receviceStock.domain.usecases.ConfirmReceiveStockUseCase
import com.nat.bless.screens.receviceStock.domain.usecases.GetReceiveStockUseCase
import com.nat.bless.screens.receviceStock.presentation.ReceiveStockViewModel
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