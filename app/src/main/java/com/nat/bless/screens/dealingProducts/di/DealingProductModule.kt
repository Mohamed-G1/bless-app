package com.nat.bless.screens.dealingProducts.di

import com.nat.bless.screens.dealingProducts.data.DealingProductRepositoryImpl
import com.nat.bless.screens.dealingProducts.domain.data.DealingProductRepository
import com.nat.bless.screens.dealingProducts.domain.usecases.GetDealingProductsUseCase
import com.nat.bless.screens.dealingProducts.peresentation.DealingProductsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dealingProductModule = module {

    single<DealingProductRepository> { DealingProductRepositoryImpl(get()) }
    single { GetDealingProductsUseCase(get()) }

    viewModel {
        DealingProductsViewModel(get(), get())
    }
}