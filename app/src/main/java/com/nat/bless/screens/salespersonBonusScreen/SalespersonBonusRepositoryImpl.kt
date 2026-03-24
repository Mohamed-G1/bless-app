package com.nat.bless.screens.salespersonBonusScreen

import com.nat.bless.base.BaseRequest
import com.nat.bless.base.BaseResponse
import com.nat.bless.base.network.ApiServices
import com.nat.bless.base.network.Resource
import com.nat.bless.base.network.safeApiCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class SalespersonBonusRepositoryImpl(
    private val apiServices: ApiServices
) : SalespersonBonusRepository {
    override suspend fun getBonusDetails(request: BaseRequest<BonusDetailsRequest>): Flow<Resource<BaseResponse<BonusDetailsResponse>>> =
        flow {
            emit(Resource.Loading)
            val result = safeApiCall {
                apiServices.getBonusDetails(request)
            }
            emit(result)
        }.catch { e ->
            emit(Resource.Error(e.message.toString()))
        }
}