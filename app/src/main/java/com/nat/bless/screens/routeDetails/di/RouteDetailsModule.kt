package com.nat.bless.screens.routeDetails.di

import com.nat.bless.screens.routeDetails.data.repository.RouteDetailsRepositoryImpl
import com.nat.bless.screens.routeDetails.domain.repository.RouteDetailsRepository
import com.nat.bless.screens.routeDetails.domain.usecases.CancelRouteUseCase
import com.nat.bless.screens.routeDetails.domain.usecases.ConfirmRouteUseCase
import com.nat.bless.screens.routeDetails.presentation.RouteDetailsViewModel
import com.nat.bless.screens.routeDetails.domain.usecases.DeliveredCourierWithPODUseCase
import com.nat.bless.screens.routeDetails.domain.usecases.GetCancelReasonsUseCase
import com.nat.bless.screens.routeDetails.domain.usecases.GetConfirmReasonsUseCase
import com.nat.bless.screens.routeDetails.domain.usecases.GetOrderHistoryUseCase
import com.nat.bless.screens.routeDetails.domain.usecases.GetRefusalReasonsUseCase
import com.nat.bless.screens.routeDetails.domain.usecases.HandleConfirmAndCancelRoutesUseCase
import com.nat.bless.screens.routeDetails.domain.usecases.UpdateWaybillCourierStatusUseCase
import com.nat.bless.screens.routeDetails.domain.usecases.SendSignatureUseCase
import com.nat.bless.screens.routeDetails.domain.usecases.StatusNotDeliveredUseCase
import com.nat.bless.screens.routeDetails.domain.usecases.UpdatePickupCourierStatusUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val courierDetailsModule = module {
    single { UpdatePickupCourierStatusUseCase(get()) }
    single { UpdateWaybillCourierStatusUseCase(get()) }
    single { SendSignatureUseCase(get()) }
    single { StatusNotDeliveredUseCase(get()) }
    single { GetRefusalReasonsUseCase(get()) }
    single { GetOrderHistoryUseCase(get()) }
    single { DeliveredCourierWithPODUseCase(get()) }


    single { GetConfirmReasonsUseCase(get()) }
    single { GetCancelReasonsUseCase(get()) }
    single { ConfirmRouteUseCase(get()) }
    single { CancelRouteUseCase(get()) }
    single { HandleConfirmAndCancelRoutesUseCase(get(), get(), get(), get()) }

    single<RouteDetailsRepository> {
        RouteDetailsRepositoryImpl(get())
    }
    viewModel {
        RouteDetailsViewModel(get(), get(), get(), get(), get(), get(), get())
    }
}