package com.nat.bless.screens.category.di

import com.nat.bless.screens.category.data.CategoryRepositoryImpl
import com.nat.bless.screens.category.domain.data.CategoryRepository
import com.nat.bless.screens.category.domain.useCases.GetCategoryDataUseCase
import com.nat.bless.screens.category.presentation.CategoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val CategoryModule = module {
    single<CategoryRepository> { CategoryRepositoryImpl(get()) }
    single { GetCategoryDataUseCase(get()) }

    viewModel {
        CategoryViewModel(
            get(),
            get()
        )
    }
}