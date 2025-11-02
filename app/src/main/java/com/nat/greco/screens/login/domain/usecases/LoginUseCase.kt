package com.nat.greco.screens.login.domain.usecases

import com.nat.greco.base.BaseRequest
import com.nat.greco.base.BaseResponse
import com.nat.greco.base.network.Resource
import com.nat.greco.screens.login.domain.models.LoginRequest
import com.nat.greco.screens.login.domain.models.LoginResponse
import com.nat.greco.screens.login.domain.repository.LoginRepository

class LoginUseCase(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(
        request: BaseRequest<LoginRequest>
    ): Resource<BaseResponse<LoginResponse>> =
        loginRepository.userLogin(request)
}