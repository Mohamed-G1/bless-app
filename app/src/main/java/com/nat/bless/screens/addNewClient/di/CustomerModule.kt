package com.nat.bless.screens.addNewClient.di

import com.nat.bless.screens.addNewClient.data.CustomerRepositoryImpl
import com.nat.bless.screens.addNewClient.domain.repository.CustomerRepository
import com.nat.bless.screens.addNewClient.domain.usecases.AddCustomerUseCase
import com.nat.bless.screens.addNewClient.domain.usecases.GetAreasUseCase
import com.nat.bless.screens.addNewClient.domain.usecases.GetCitiesUseCase
import com.nat.bless.screens.addNewClient.domain.usecases.GetCountryUseCase
import com.nat.bless.screens.addNewClient.domain.usecases.GetCustomersUseCase
import com.nat.bless.screens.addNewClient.domain.usecases.GetStatesUseCase
import com.nat.bless.screens.addNewClient.domain.usecases.GetTagsUseCase
import com.nat.bless.screens.addNewClient.presentation.AddNewCustomerViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val CustomerModule = module {
    single<CustomerRepository> { CustomerRepositoryImpl(get()) }
    single { GetCustomersUseCase(get()) }
    single { GetCitiesUseCase(get()) }
    single { GetCountryUseCase(get()) }
    single { GetStatesUseCase(get()) }
    single { AddCustomerUseCase(get()) }
    single { GetAreasUseCase(get()) }
    single { GetTagsUseCase(get()) }

    viewModel {
        AddNewCustomerViewModel(get(), get(), get(), get(), get(),get(), get(), get())
    }
}