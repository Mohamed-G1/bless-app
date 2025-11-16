package com.nat.greco.screens.returnsScreen

import com.nat.greco.base.BaseRequest

class SendReturnsUseCase(
    private val repository: ReturnsRepository
) {
    suspend operator fun invoke(request: BaseRequest<ReturnsRequest>) =
        repository.sendReturns(request)
}