package com.nat.greco.screens.addNewClient.domain.usecases

import com.nat.greco.base.BaseRequest
import com.nat.greco.screens.addNewClient.domain.models.CustomerRequest
import com.nat.greco.screens.addNewClient.domain.repository.CustomerRepository

class GetCustomersUseCase(
    private val repository: CustomerRepository
) {

    suspend operator fun invoke(request: BaseRequest<CustomerRequest>) =
        repository.getCustomersList(request)
}