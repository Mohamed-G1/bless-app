package com.nat.greco.screens.addNewClient.domain.usecases

import com.nat.greco.base.BaseRequest
import com.nat.greco.screens.addNewClient.domain.models.CitiesRequest
import com.nat.greco.screens.addNewClient.domain.repository.CustomerRepository

class GetCitiesUseCase(
    private val repository: CustomerRepository

) {
    suspend operator fun invoke(request: BaseRequest<CitiesRequest>) =
        repository.getCities(request)
}