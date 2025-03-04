package com.nat.couriersapp.screens.courierDetails.di

import com.nat.couriersapp.screens.courierDetails.data.repository.CourierDetailsRepositoryImpl
import com.nat.couriersapp.screens.courierDetails.domain.repository.CourierDetailsRepository
import com.nat.couriersapp.screens.courierDetails.presentation.CourierDetailsViewModel
import com.nat.couriersapp.screens.courierDetails.domain.usecases.DeliveredCourierUseCase
import com.nat.couriersapp.screens.courierDetails.domain.usecases.GetRefusalReasonsUseCase
import com.nat.couriersapp.screens.courierDetails.domain.usecases.NotDeliveredCourierUseCase
import com.nat.couriersapp.screens.courierDetails.domain.usecases.SendSignatureUseCase
import com.nat.couriersapp.screens.courierDetails.domain.usecases.StatusNotDeliveredUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val courierDetailsModule = module {
    single { DeliveredCourierUseCase(get()) }
    single { NotDeliveredCourierUseCase(get()) }
    single { SendSignatureUseCase(get()) }
    single { StatusNotDeliveredUseCase(get()) }
    single { GetRefusalReasonsUseCase(get()) }
    single<CourierDetailsRepository> {
        CourierDetailsRepositoryImpl(get())
    }
    viewModel {
        CourierDetailsViewModel(get(), get(), get(), get(), get())
    }
}