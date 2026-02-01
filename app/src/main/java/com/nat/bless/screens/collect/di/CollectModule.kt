package com.nat.bless.screens.collect.di

import com.nat.bless.screens.collect.data.CollectRepositoryImpl
import com.nat.bless.screens.collect.domain.repository.CollectRepository
import com.nat.bless.screens.collect.domain.usecases.CreatePaymentUseCase
import com.nat.bless.screens.collect.domain.usecases.GetCustomerSummeryUseCase
import com.nat.bless.screens.collect.domain.usecases.GetJournalsUseCase
import com.nat.bless.screens.collect.presenation.CollectViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val CollectModule = module {

    single<CollectRepository> { CollectRepositoryImpl(get()) }
    single { GetJournalsUseCase(get()) }
    single { CreatePaymentUseCase(get()) }
    single { GetCustomerSummeryUseCase(get()) }

    viewModel {
        CollectViewModel(
            get(),
            get(),
            get(),get()
        )
    }
}