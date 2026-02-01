package com.nat.bless.screens.priceList.di

import com.nat.bless.screens.priceList.data.PriceListRepositoryImpl
import com.nat.bless.screens.priceList.domain.data.PriceListRepository
import com.nat.bless.screens.priceList.domain.usecases.GetPriceListUseCase
import com.nat.bless.screens.priceList.presentation.PriceListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val priceListModule = module {
    single<PriceListRepository> { PriceListRepositoryImpl(get()) }
    single { GetPriceListUseCase(get()) }
    viewModel {
        PriceListViewModel(
            getUserDataManager = get(),
            getPriceListUseCase = get()
        )
    }
}