package com.nat.greco.screens.addNewClient.domain.repository

import com.nat.greco.base.BaseRequest
import com.nat.greco.base.BaseResponse
import com.nat.greco.base.network.Resource
import com.nat.greco.screens.addNewClient.domain.models.AddCustomerRequest
import com.nat.greco.screens.addNewClient.domain.models.AreasResponse
import com.nat.greco.screens.addNewClient.domain.models.CitiesRequest
import com.nat.greco.screens.addNewClient.domain.models.CountryResponse
import com.nat.greco.screens.addNewClient.domain.models.CustomerRequest
import com.nat.greco.screens.addNewClient.domain.models.CustomerResponse
import com.nat.greco.screens.addNewClient.domain.models.StatesRequest
import com.nat.greco.screens.addNewClient.domain.models.StatesResponse
import kotlinx.coroutines.flow.Flow

interface CustomerRepository {
    suspend fun getCustomersList(request: BaseRequest<CustomerRequest>): Flow<Resource<BaseResponse<List<CustomerResponse>>>>
    suspend fun getCountries(request: BaseRequest<CustomerRequest>): Flow<Resource<BaseResponse<List<CountryResponse>>>>
    suspend fun getStates(request: BaseRequest<StatesRequest>): Flow<Resource<BaseResponse<List<StatesResponse>>>>
    suspend fun getCities(request: BaseRequest<CitiesRequest>): Flow<Resource<BaseResponse<List<StatesResponse>>>>
    suspend fun getAreas(request: BaseRequest<CustomerRequest>): Flow<Resource<BaseResponse<List<AreasResponse>>>>


    suspend fun addCustomer(request: BaseRequest<AddCustomerRequest>): Resource<BaseResponse<Any>>
}