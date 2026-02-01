package com.nat.bless.screens.collect.domain.usecases

import com.nat.bless.base.BaseRequest
import com.nat.bless.screens.collect.domain.models.CreatePaymentRequest
import com.nat.bless.screens.collect.domain.repository.CollectRepository

class CreatePaymentUseCase(
    val repository: CollectRepository
) {

    suspend operator fun invoke(request: BaseRequest<CreatePaymentRequest>) =
        repository.createPayment(request)
}