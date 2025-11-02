package com.nat.greco.screens.priceList.data

import com.nat.greco.base.BaseRequest
import com.nat.greco.base.network.ApiServices
import com.nat.greco.base.network.Resource
import com.nat.greco.base.network.safeApiCall
import com.nat.greco.screens.priceList.domain.data.PriceListRepository
import com.nat.greco.screens.priceList.domain.models.PriceListRequest
import com.nat.greco.screens.priceList.domain.models.PriceListResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class PriceListRepositoryImpl(
    private val apiServices: ApiServices
) : PriceListRepository {
    override suspend fun getPriceList(request: BaseRequest<PriceListRequest>): Flow<Resource<PriceListResponse>> =
        flow {
            emit(Resource.Loading)
            val result = safeApiCall {
                apiServices.getPriceList(request)
            }
            emit(result)
        }.catch { e ->
            emit(Resource.Error(e.message.toString()))
        }
}