package com.nat.greco.screens.addNewClient.domain.usecases

import com.nat.greco.base.BaseRequest
import com.nat.greco.screens.addNewClient.domain.models.CitiesRequest
import com.nat.greco.screens.addNewClient.domain.models.CustomerRequest
import com.nat.greco.screens.addNewClient.domain.repository.CustomerRepository

class GetAreasUseCase(
    private val repository: CustomerRepository

) {
    suspend operator fun invoke(request: BaseRequest<CustomerRequest>) =
        repository.getAreas(request)
}