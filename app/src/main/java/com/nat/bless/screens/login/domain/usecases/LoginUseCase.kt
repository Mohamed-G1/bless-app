package com.nat.bless.screens.login.domain.usecases

import com.nat.bless.base.BaseRequest
import com.nat.bless.base.BaseResponse
import com.nat.bless.base.network.Resource
import com.nat.bless.screens.login.domain.models.LoginRequest
import com.nat.bless.screens.login.domain.models.LoginResponse
import com.nat.bless.screens.login.domain.repository.LoginRepository

class LoginUseCase(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(
        request: BaseRequest<LoginRequest>
    ): Resource<BaseResponse<LoginResponse>> =
        loginRepository.userLogin(request)
}