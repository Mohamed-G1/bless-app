package com.nat.greco.screens.home.domain.usecases

import com.nat.greco.base.network.Resource
import com.nat.greco.screens.home.domain.repository.HomeRepository

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