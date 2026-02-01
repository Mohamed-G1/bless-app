package com.nat.bless.screens.addNewClient.domain.usecases

import com.nat.bless.base.BaseRequest
import com.nat.bless.screens.addNewClient.domain.models.CitiesRequest
import com.nat.bless.screens.addNewClient.domain.repository.CustomerRepository

class GetCitiesUseCase(
    private val repository: CustomerRepository

) {
    suspend operator fun invoke(request: BaseRequest<CitiesRequest>) =
        repository.getCities(request)
}