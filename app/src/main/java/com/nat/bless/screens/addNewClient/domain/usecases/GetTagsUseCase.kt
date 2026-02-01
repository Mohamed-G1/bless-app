package com.nat.bless.screens.addNewClient.domain.usecases


import com.nat.bless.screens.addNewClient.domain.repository.CustomerRepository

class GetTagsUseCase (
    private val repository: CustomerRepository

) {

    suspend operator fun invoke() =
        repository.getTags()
}