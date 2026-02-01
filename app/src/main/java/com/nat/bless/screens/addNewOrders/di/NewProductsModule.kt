package com.nat.bless.screens.addNewOrders.di

import com.nat.bless.screens.addNewOrders.data.AddNewProductRepositoryImpl
import com.nat.bless.screens.addNewOrders.domain.data.AddNewProductRepository
import com.nat.bless.screens.addNewOrders.domain.usecases.AddToCartUseCase
import com.nat.bless.screens.addNewOrders.domain.usecases.GetProductsListUseCase
import com.nat.bless.screens.addNewOrders.presentation.NewProductsViewModel
import com.nat.bless.screens.addNewOrders.presentation.chooseCustomer.ChooseCustomerViewModel
import com.nat.bless.screens.clients.CustomersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val newProductsModule= module {
    single<AddNewProductRepository> { AddNewProductRepositoryImpl(get()) }

    single { GetProductsListUseCase(get()) }
    single { AddToCartUseCase(get()) }

    viewModel { NewProductsViewModel(get(), get(), get()) }
    viewModel { ChooseCustomerViewModel(get(), get()) }
    viewModel { CustomersViewModel(get(), get()) }
}