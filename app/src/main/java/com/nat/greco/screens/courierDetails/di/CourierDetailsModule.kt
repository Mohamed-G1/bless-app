package com.nat.greco.screens.courierDetails.di

import com.nat.greco.screens.courierDetails.data.repository.CourierDetailsRepositoryImpl
import com.nat.greco.screens.courierDetails.domain.repository.CourierDetailsRepository
import com.nat.greco.screens.courierDetails.presentation.CourierDetailsViewModel
import com.nat.greco.screens.courierDetails.domain.usecases.DeliveredCourierWithPODUseCase
import com.nat.greco.screens.courierDetails.domain.usecases.GetRefusalReasonsUseCase
import com.nat.greco.screens.courierDetails.domain.usecases.UpdateWaybillCourierStatusUseCase
import com.nat.greco.screens.courierDetails.domain.usecases.SendSignatureUseCase
import com.nat.greco.screens.courierDetails.domain.usecases.StatusNotDeliveredUseCase
import com.nat.greco.screens.courierDetails.domain.usecases.UpdatePickupCourierStatusUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val courierDetailsModule = module {
    single { UpdatePickupCourierStatusUseCase(get()) }
    single { UpdateWaybillCourierStatusUseCase(get()) }
    single { SendSignatureUseCase(get()) }
    single { StatusNotDeliveredUseCase(get()) }
    single { GetRefusalReasonsUseCase(get()) }
    single { DeliveredCourierWithPODUseCase(get()) }
    single<CourierDetailsRepository> {
        CourierDetailsRepositoryImpl(get())
    }
    viewModel {
        CourierDetailsViewModel(get(), get(), get(), get(), get(), get())
    }
}