package com.nat.bless.screens.orderDetails.di

import com.nat.bless.screens.confirmOrder.ConfirmOrderViewModel
import com.nat.bless.screens.editableConfirmOrder.EditableConfirmOrderViewModel
import com.nat.bless.screens.orderDetails.data.OrderDetailsRepositoryImpl
import com.nat.bless.screens.orderDetails.domain.repository.OrderDetailsRepository
import com.nat.bless.screens.orderDetails.domain.usecases.ConfirmOrderUseCase
import com.nat.bless.screens.orderDetails.domain.usecases.DeleteOrderListUseCase
import com.nat.bless.screens.orderDetails.domain.usecases.GetEditableOrderListUseCase
import com.nat.bless.screens.orderDetails.domain.usecases.GetOrderDetailsUseCase
import com.nat.bless.screens.orderDetails.domain.usecases.UpdateOrderListUseCase
import com.nat.bless.screens.orderDetails.presentation.OrderDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val OrderDetailsModule = module {
    single<OrderDetailsRepository> { OrderDetailsRepositoryImpl(get()) }
    single { GetOrderDetailsUseCase(get()) }
    single { ConfirmOrderUseCase(get()) }
    single { GetEditableOrderListUseCase(get()) }
    single { UpdateOrderListUseCase(get()) }
    single { DeleteOrderListUseCase(get()) }
    viewModel {
        OrderDetailsViewModel(get(), get())
    }

    viewModel {
        ConfirmOrderViewModel(get(), get(), get())
    }

    viewModel{
        EditableConfirmOrderViewModel(get(), get(), get(), get())
    }
}