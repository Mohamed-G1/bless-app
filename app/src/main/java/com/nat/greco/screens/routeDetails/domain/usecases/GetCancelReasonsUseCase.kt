package com.nat.greco.screens.routeDetails.domain.usecases

import com.nat.greco.screens.routeDetails.domain.repository.RouteDetailsRepository

class GetCancelReasonsUseCase (
    private val repository: RouteDetailsRepository
) {
    suspend operator fun invoke() =
        repository.getCancelledReasons()
}