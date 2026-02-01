package com.nat.bless.screens.accounts.di

import com.nat.bless.screens.accounts.data.AccountsRepositoryImpl
import com.nat.bless.screens.accounts.domain.data.AccountsRepository
import com.nat.bless.screens.accounts.domain.usecases.GetInvociesUseCase
import com.nat.bless.screens.accounts.presentation.AccountsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val accountsModule = module {
    single<AccountsRepository> { AccountsRepositoryImpl(get()) }

    single { GetInvociesUseCase(get()) }

    viewModel { AccountsViewModel(get(), get()) }
}