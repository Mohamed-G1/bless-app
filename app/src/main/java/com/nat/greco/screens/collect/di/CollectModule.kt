package com.nat.greco.screens.collect.di

import com.nat.greco.screens.collect.data.CollectRepositoryImpl
import com.nat.greco.screens.collect.domain.repository.CollectRepository
import com.nat.greco.screens.collect.domain.usecases.CreatePaymentUseCase
import com.nat.greco.screens.collect.domain.usecases.GetJournalsUseCase
import com.nat.greco.screens.collect.presenation.CollectViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val CollectModule = module {

    single<CollectRepository> { CollectRepositoryImpl(get()) }
    single { GetJournalsUseCase(get()) }
    single { CreatePaymentUseCase(get()) }

    viewModel {
        CollectViewModel(
            get(),
            get(),
            get()
        )
    }
}