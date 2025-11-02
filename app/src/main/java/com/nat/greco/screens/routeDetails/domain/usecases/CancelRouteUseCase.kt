package com.nat.greco.screens.routeDetails.domain.usecases

import com.nat.greco.base.BaseRequest
import com.nat.greco.screens.routeDetails.domain.models.ConfirmedAndCancelledRequest
import com.nat.greco.screens.routeDetails.domain.repository.RouteDetailsRepository

class CancelRouteUseCase(
    private val repository: RouteDetailsRepository
) {
    suspend operator fun invoke(requestBody: BaseRequest<ConfirmedAndCancelledRequest>) =
        repository.cancelRoute(requestBody)
}