package com.nat.bless.screens.login.data.repository

import com.nat.bless.base.BaseRequest
import com.nat.bless.base.BaseResponse
import com.nat.bless.base.network.ApiServices
import com.nat.bless.base.network.Resource
import com.nat.bless.base.network.safeApiCall
import com.nat.bless.screens.login.domain.models.LoginRequest
import com.nat.bless.screens.login.domain.models.LoginResponse
import com.nat.bless.screens.login.domain.repository.LoginRepository

class LoginRepositoryImpl(
    private val apiServices: ApiServices
) : LoginRepository {
    override suspend fun userLogin(
        request: BaseRequest<LoginRequest>
    ): Resource<BaseResponse<LoginResponse>> {
        return safeApiCall {
            apiServices.userLogin(request)
        }
    }

}