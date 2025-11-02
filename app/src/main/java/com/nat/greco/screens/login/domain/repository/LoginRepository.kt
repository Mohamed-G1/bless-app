package com.nat.greco.screens.login.domain.repository

import com.nat.greco.base.BaseRequest
import com.nat.greco.base.BaseResponse
import com.nat.greco.base.network.Resource
import com.nat.greco.screens.login.domain.models.LoginRequest
import com.nat.greco.screens.login.domain.models.LoginResponse

interface LoginRepository {
    suspend fun userLogin(
        request: BaseRequest<LoginRequest>
    ): Resource<BaseResponse<LoginResponse>>

}