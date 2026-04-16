package com.nat.bless.screens.category.data

import com.nat.bless.base.BaseRequest
import com.nat.bless.base.BaseResponse
import com.nat.bless.base.network.ApiServices
import com.nat.bless.base.network.Resource
import com.nat.bless.base.network.safeApiCall
import com.nat.bless.screens.category.domain.data.CategoryRepository
import com.nat.bless.screens.category.domain.models.CategoryRequest
import com.nat.bless.screens.category.domain.models.CategoryResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class CategoryRepositoryImpl(
    private val apiServices: ApiServices
) : CategoryRepository {
    override suspend fun getCategoryData(request: BaseRequest<CategoryRequest>): Flow<Resource<BaseResponse<List<CategoryResponse>>>> =
        flow {
            emit(Resource.Loading)
            val result = safeApiCall {
                apiServices.getCategoryData(request)
            }
            emit(result)
        }.catch { e ->
            emit(Resource.Error(e.message.toString()))
        }

}