package com.nat.greco.screens.login.domain.usecases

import com.nat.greco.base.network.Resource
import com.nat.greco.screens.login.domain.models.LoginRequest
import com.nat.greco.screens.login.domain.models.UserResponse
import com.nat.greco.screens.login.domain.repository.LoginRepository

class LoginUseCase(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(
        request: LoginRequest
    ): Resource<UserResponse>  =
        loginRepository.userLogin(request)
}