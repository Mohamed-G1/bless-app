package com.nat.bless.screens.routeDetails.domain.usecases

data class HandleConfirmAndCancelRoutesUseCase (
    val getCancelReasonsUseCase: GetCancelReasonsUseCase,
    val getConfirmReasonsUseCase: GetConfirmReasonsUseCase,
    val confirmRouteUseCase: ConfirmRouteUseCase,
    val cancelRouteUseCase: CancelRouteUseCase
)