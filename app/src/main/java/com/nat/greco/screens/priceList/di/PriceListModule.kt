package com.nat.greco.screens.priceList.di

import com.nat.greco.screens.priceList.data.PriceListRepositoryImpl
import com.nat.greco.screens.priceList.domain.data.PriceListRepository
import com.nat.greco.screens.priceList.domain.usecases.GetPriceListUseCase
import com.nat.greco.screens.priceList.presentation.PriceListViewModel
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