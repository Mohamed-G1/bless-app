package com.nat.greco.screens.login.data.repository

import android.util.Log
import com.nat.greco.base.BaseRequest
import com.nat.greco.base.BaseResponse
import com.nat.greco.base.network.ApiServices
import com.nat.greco.base.network.Resource
import com.nat.greco.base.network.safeApiCall
import com.nat.greco.screens.login.domain.models.LoginRequest
import com.nat.greco.screens.login.domain.models.LoginResponse
import com.nat.greco.screens.login.domain.repository.LoginRepository

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