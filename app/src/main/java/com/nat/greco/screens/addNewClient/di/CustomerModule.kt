package com.nat.greco.screens.addNewClient.di

import com.nat.greco.screens.addNewClient.data.CustomerRepositoryImpl
import com.nat.greco.screens.addNewClient.domain.repository.CustomerRepository
import com.nat.greco.screens.addNewClient.domain.usecases.AddCustomerUseCase
import com.nat.greco.screens.addNewClient.domain.usecases.GetCustomersUseCase
import com.nat.greco.screens.addNewClient.presentation.AddNewCustomerViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val CustomerModule = module {
    single<CustomerRepository> { CustomerRepositoryImpl(get()) }
    single { GetCustomersUseCase(get()) }
    single { AddCustomerUseCase(get()) }

    viewModel {
        AddNewCustomerViewModel(get(), get(), get())
    }
}