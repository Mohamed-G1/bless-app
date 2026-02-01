package com.nat.bless.screens.collect.domain.usecases

import com.nat.bless.base.BaseRequest
import com.nat.bless.screens.collect.domain.models.CustomerSummeryRequest
import com.nat.bless.screens.collect.domain.repository.CollectRepository

class GetCustomerSummeryUseCase(
    val repository: CollectRepository
) {
    suspend operator fun invoke(request: BaseRequest<CustomerSummeryRequest>) =
        repository.getCustomerSummery(request)
}