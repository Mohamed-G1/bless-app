package com.nat.bless.screens.returnsScreen

import com.nat.bless.base.BaseRequest

class SendReturnsUseCase(
    private val repository: ReturnsRepository
) {
    suspend operator fun invoke(request: BaseRequest<ReturnsRequest>) =
        repository.sendReturns(request)
}