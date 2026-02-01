package com.nat.bless.screens.routeDetails.domain.usecases

import com.nat.bless.base.BaseRequest
import com.nat.bless.screens.routeDetails.domain.models.ConfirmedAndCancelledRequest
import com.nat.bless.screens.routeDetails.domain.repository.RouteDetailsRepository

class CancelRouteUseCase(
    private val repository: RouteDetailsRepository
) {
    suspend operator fun invoke(requestBody: BaseRequest<ConfirmedAndCancelledRequest>) =
        repository.cancelRoute(requestBody)
}