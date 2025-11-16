package com.nat.greco.screens.addNewClient.domain.repository

import com.nat.greco.base.BaseRequest
import com.nat.greco.base.BaseResponse
import com.nat.greco.base.network.Resource
import com.nat.greco.screens.addNewClient.domain.models.AddCustomerRequest
import com.nat.greco.screens.addNewClient.domain.models.CustomerRequest
import com.nat.greco.screens.addNewClient.domain.models.CustomerResponse
import kotlinx.coroutines.flow.Flow

interface CustomerRepository {
    suspend fun getCustomersList(request: BaseRequest<CustomerRequest>): Flow<Resource<BaseResponse<List<CustomerResponse>>>>
    suspend fun addCustomer(request: BaseRequest<AddCustomerRequest>): Resource<BaseResponse<Any>>
}