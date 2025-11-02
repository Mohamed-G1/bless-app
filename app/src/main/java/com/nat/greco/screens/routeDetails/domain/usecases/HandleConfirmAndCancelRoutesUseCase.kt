package com.nat.greco.screens.routeDetails.domain.usecases

data class HandleConfirmAndCancelRoutesUseCase (
    val getCancelReasonsUseCase: GetCancelReasonsUseCase,
    val getConfirmReasonsUseCase: GetConfirmReasonsUseCase,
    val confirmRouteUseCase: ConfirmRouteUseCase,
    val cancelRouteUseCase: CancelRouteUseCase
)