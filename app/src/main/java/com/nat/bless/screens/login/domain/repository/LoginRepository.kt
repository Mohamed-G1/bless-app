package com.nat.bless.screens.login.domain.repository

import com.nat.bless.base.BaseRequest
import com.nat.bless.base.BaseResponse
import com.nat.bless.base.network.Resource
import com.nat.bless.screens.login.domain.models.LoginRequest
import com.nat.bless.screens.login.domain.models.LoginResponse

interface LoginRepository {
    suspend fun userLogin(
        request: BaseRequest<LoginRequest>
    ): Resource<BaseResponse<LoginResponse>>

}