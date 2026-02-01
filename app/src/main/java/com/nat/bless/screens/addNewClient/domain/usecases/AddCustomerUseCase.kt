package com.nat.bless.screens.addNewClient.domain.usecases

import com.nat.bless.base.BaseRequest
import com.nat.bless.screens.addNewClient.domain.models.AddCustomerRequest
import com.nat.bless.screens.addNewClient.domain.repository.CustomerRepository

class AddCustomerUseCase(
    private val repository: CustomerRepository
) {
    suspend operator fun invoke(request: BaseRequest<AddCustomerRequest>) =
        repository.addCustomer(request)
}