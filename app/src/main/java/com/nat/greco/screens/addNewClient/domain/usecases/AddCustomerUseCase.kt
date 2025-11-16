package com.nat.greco.screens.addNewClient.domain.usecases

import com.nat.greco.base.BaseRequest
import com.nat.greco.screens.addNewClient.domain.models.AddCustomerRequest
import com.nat.greco.screens.addNewClient.domain.repository.CustomerRepository

class AddCustomerUseCase(
    private val repository: CustomerRepository
) {
    suspend operator fun invoke(request: BaseRequest<AddCustomerRequest>) =
        repository.addCustomer(request)
}