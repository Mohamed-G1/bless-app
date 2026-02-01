package com.nat.bless.screens.promotionsList.di

import com.nat.bless.screens.promotionsList.data.PromotionRepositoryImpl
import com.nat.bless.screens.promotionsList.domain.repository.PromotionRepository
import com.nat.bless.screens.promotionsList.domain.usecases.GetPromotionsListUseCase
import com.nat.bless.screens.promotionsList.presentation.PromotionViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val PromotionModule = module {
    single<PromotionRepository> { PromotionRepositoryImpl(get()) }
    single { GetPromotionsListUseCase(get()) }
    viewModel {
        PromotionViewModel(get(), get())
    }
}