package com.nat.bless.screens.addNewClient.domain.usecases

import com.nat.bless.base.BaseRequest
import com.nat.bless.screens.addNewClient.domain.models.CustomerRequest
import com.nat.bless.screens.addNewClient.domain.repository.CustomerRepository

class GetAreasUseCase(
    private val repository: CustomerRepository

) {
    suspend operator fun invoke(request: BaseRequest<CustomerRequest>) =
        repository.getAreas(request)
}