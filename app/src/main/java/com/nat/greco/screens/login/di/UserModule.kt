package com.nat.greco.screens.login.di

import com.nat.greco.screens.login.data.manager.LoginManagerImpl
import com.nat.greco.screens.login.data.repository.LoginRepositoryImpl
import com.nat.greco.screens.login.domain.manager.LoginManager
import com.nat.greco.screens.login.domain.repository.LoginRepository
import com.nat.greco.screens.login.domain.usecases.ClearUserUseCase
import com.nat.greco.screens.login.domain.usecases.LoginUseCase
import com.nat.greco.screens.login.domain.usecases.SaveUserUseCase
import com.nat.greco.screens.login.presentation.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val userModule = module {
    single<LoginRepository> { LoginRepositoryImpl(get()) }
    single { LoginUseCase(get()) }
    single<LoginManager> {
        LoginManagerImpl(get())
    }
    single {
        SaveUserUseCase(get())
    }

    single {
        ClearUserUseCase(get())
    }
    viewModel {
        LoginViewModel(get(), get())
    }
}