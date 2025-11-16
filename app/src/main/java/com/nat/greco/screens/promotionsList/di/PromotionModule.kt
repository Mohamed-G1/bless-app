package com.nat.greco.screens.promotionsList.di

import com.nat.greco.screens.promotionsList.data.PromotionRepositoryImpl
import com.nat.greco.screens.promotionsList.domain.repository.PromotionRepository
import com.nat.greco.screens.promotionsList.domain.usecases.GetPromotionsListUseCase
import com.nat.greco.screens.promotionsList.presentation.PromotionViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val PromotionModule = module {
    single<PromotionRepository> { PromotionRepositoryImpl(get()) }
    single { GetPromotionsListUseCase(get()) }
    viewModel {
        PromotionViewModel(get(), get())
    }
}