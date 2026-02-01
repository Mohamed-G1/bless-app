package com.nat.bless.screens.addNewClient.domain.usecases

import com.nat.bless.base.BaseRequest
import com.nat.bless.screens.addNewClient.domain.models.StatesRequest
import com.nat.bless.screens.addNewClient.domain.repository.CustomerRepository

class GetStatesUseCase(
    private val repository: CustomerRepository

) {

    suspend operator fun invoke(request: BaseRequest<StatesRequest>) =
        repository.getStates(request)
}