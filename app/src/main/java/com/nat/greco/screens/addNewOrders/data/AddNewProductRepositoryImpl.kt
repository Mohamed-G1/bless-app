package com.nat.greco.screens.addNewOrders.data

import com.nat.greco.base.BaseRequest
import com.nat.greco.base.BaseResponse
import com.nat.greco.base.network.ApiServices
import com.nat.greco.base.network.Resource
import com.nat.greco.base.network.safeApiCall
import com.nat.greco.screens.addNewOrders.domain.data.AddNewProductRepository
import com.nat.greco.screens.addNewOrders.models.AddToCartRequest
import com.nat.greco.screens.addNewOrders.models.AddToCartResponse
import com.nat.greco.screens.addNewOrders.models.NewProductRequest
import com.nat.greco.screens.addNewOrders.models.NewProductsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class AddNewProductRepositoryImpl(
    private val apiService: ApiServices
) : AddNewProductRepository {
    override suspend fun getProductsList(request: BaseRequest<NewProductRequest>): Flow<Resource<NewProductsResponse>> =
        flow {
            emit(Resource.Loading)
            val result = safeApiCall {
                apiService.getProductsListHistory(request)
            }
            emit(result)
        }.catch { exception ->
            emit(Resource.Error(message = exception.message.orEmpty()))
        }

    override suspend fun addToCart(request: BaseRequest<AddToCartRequest>): Flow<Resource<BaseResponse<AddToCartResponse>>> =
            flow {
                emit(Resource.Loading)
                val result = safeApiCall {
                    apiService.addToCart(request)
                }
                emit(result)
            }.catch { exception ->
                emit(Resource.Error(message = exception.message.orEmpty()))
            }


}