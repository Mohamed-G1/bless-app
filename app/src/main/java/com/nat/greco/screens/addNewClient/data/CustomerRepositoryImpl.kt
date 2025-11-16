package com.nat.greco.screens.addNewClient.data

import com.nat.greco.base.BaseRequest
import com.nat.greco.base.BaseResponse
import com.nat.greco.base.network.ApiServices
import com.nat.greco.base.network.Resource
import com.nat.greco.base.network.safeApiCall
import com.nat.greco.screens.addNewClient.domain.models.AddCustomerRequest
import com.nat.greco.screens.addNewClient.domain.models.CustomerRequest
import com.nat.greco.screens.addNewClient.domain.models.CustomerResponse
import com.nat.greco.screens.addNewClient.domain.repository.CustomerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class CustomerRepositoryImpl(
    val apiServices: ApiServices
) : CustomerRepository {
    override suspend fun getCustomersList(request: BaseRequest<CustomerRequest>): Flow<Resource<BaseResponse<List<CustomerResponse>>>> =
        flow {
            emit(Resource.Loading)
            val result = safeApiCall {
                apiServices.getCustomers(request)
            }
            emit(result)
        }.catch { e ->
            emit(Resource.Error(e.message.toString()))
        }

    override suspend fun addCustomer(request: BaseRequest<AddCustomerRequest>): Resource<BaseResponse<Any>> =
        safeApiCall {
            apiServices.addNewCustomer(request)
        }
}