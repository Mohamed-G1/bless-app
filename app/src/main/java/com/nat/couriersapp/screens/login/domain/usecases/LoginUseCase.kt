package com.nat.couriersapp.screens.login.domain.usecases

import com.nat.couriersapp.base.network.Resource
import com.nat.couriersapp.screens.login.domain.models.LoginRequest
import com.nat.couriersapp.screens.login.domain.models.UserResponse
import com.nat.couriersapp.screens.login.domain.repository.LoginRepository

class LoginUseCase(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(
        request: LoginRequest
    ): Resource<UserResponse>  =
        loginRepository.userLogin(request)
}