package com.nat.bless.screens.home.domain.usecases

import com.nat.bless.base.network.Resource
import com.nat.bless.screens.home.domain.repository.HomeRepository

class SendLocationUseCase(
    private val repository: HomeRepository
) {
    suspend operator fun invoke(
        userId: Int,
        latitude: String,
        longitude: String
    ): Resource<Any> {
        return repository.sendLocation(userId, latitude, longitude)
    }
}