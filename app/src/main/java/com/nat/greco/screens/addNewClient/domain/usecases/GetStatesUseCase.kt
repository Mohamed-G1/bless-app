package com.nat.greco.screens.addNewClient.domain.usecases

import com.nat.greco.base.BaseRequest
import com.nat.greco.screens.addNewClient.domain.models.StatesRequest
import com.nat.greco.screens.addNewClient.domain.repository.CustomerRepository

class GetStatesUseCase(
    private val repository: CustomerRepository

) {

    suspend operator fun invoke(request: BaseRequest<StatesRequest>) =
        repository.getStates(request)
}