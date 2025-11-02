package com.nat.greco.screens.stocks.di

import com.nat.greco.screens.stocks.data.StocksRepositoryImpl
import com.nat.greco.screens.stocks.domain.repository.StocksRepository
import com.nat.greco.screens.stocks.domain.usecases.GetReturnsStockUseCase
import com.nat.greco.screens.stocks.domain.usecases.SearchReturnsUseCase
import com.nat.greco.screens.stocks.domain.usecases.SearchStockUseCase
import com.nat.greco.screens.stocks.peresentation.StockViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val stocksModule = module {
    single<StocksRepository> { StocksRepositoryImpl(get()) }
    single { GetReturnsStockUseCase(get()) }
    single { SearchStockUseCase(get()) }
    single { SearchReturnsUseCase(get()) }

    viewModel {
        StockViewModel(get(), get(), get(), get(), get())
    }

}