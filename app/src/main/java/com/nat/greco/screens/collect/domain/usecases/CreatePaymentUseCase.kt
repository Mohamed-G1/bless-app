package com.nat.greco.screens.collect.domain.usecases

import com.nat.greco.base.BaseRequest
import com.nat.greco.screens.collect.domain.models.CreatePaymentRequest
import com.nat.greco.screens.collect.domain.repository.CollectRepository

class CreatePaymentUseCase(
    val repository: CollectRepository
) {

    suspend operator fun invoke(request: BaseRequest<CreatePaymentRequest>) =
        repository.createPayment(request)
}