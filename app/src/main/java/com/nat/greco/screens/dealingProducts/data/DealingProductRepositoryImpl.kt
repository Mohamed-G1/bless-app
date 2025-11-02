package com.nat.greco.screens.dealingProducts.data

import com.nat.greco.base.BaseRequest
import com.nat.greco.base.network.ApiServices
import com.nat.greco.base.network.Resource
import com.nat.greco.base.network.safeApiCall
import com.nat.greco.screens.dealingProducts.domain.data.DealingProductRepository
import com.nat.greco.screens.dealingProducts.models.DealingProductsRequest
import com.nat.greco.screens.dealingProducts.models.DealingProductsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class DealingProductRepositoryImpl(
    private val apiServices: ApiServices
) : DealingProductRepository {
    override suspend fun getDealingProducts(request: BaseRequest<DealingProductsRequest>): Flow<Resource<DealingProductsResponse>> =
        flow {
            emit(Resource.Loading)
            val result = safeApiCall {
                apiServices.getDealingProducts(request = request)
            }
            emit(result)
        }.catch { exception ->
            emit(Resource.Error(message = exception.message.orEmpty()))
        }
}