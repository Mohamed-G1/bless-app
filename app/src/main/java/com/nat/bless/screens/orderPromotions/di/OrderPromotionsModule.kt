package com.nat.bless.screens.orderPromotions.di

import com.nat.bless.screens.orderPromotions.data.OrderPromotionsRepositoryImpl
import com.nat.bless.screens.orderPromotions.domain.repository.OrderPromotionsRepository
import com.nat.bless.screens.orderPromotions.peresenation.OrderPromotionsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val OrderPromotionsModule = module {
    single<OrderPromotionsRepository> { OrderPromotionsRepositoryImpl(get()) }
    viewModel { OrderPromotionsViewModel(get(), get()) }
}