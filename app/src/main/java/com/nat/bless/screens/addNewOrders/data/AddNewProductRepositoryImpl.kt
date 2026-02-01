package com.nat.bless.screens.addNewOrders.data

import com.nat.bless.base.BaseRequest
import com.nat.bless.base.network.ApiServices
import com.nat.bless.base.network.Resource
import com.nat.bless.base.network.safeApiCall
import com.nat.bless.screens.addNewOrders.domain.data.AddNewProductRepository
import com.nat.bless.screens.addNewOrders.models.AddToCartRequest
import com.nat.bless.screens.addNewOrders.models.NewProductRequest
import com.nat.bless.screens.addNewOrders.models.NewProductsResponse
import com.nat.bless.screens.addNewOrders.models.addTocart.AddToCartResponse
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

    override suspend fun addToCart(request: BaseRequest<AddToCartRequest>): Flow<Resource<AddToCartResponse>> =
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