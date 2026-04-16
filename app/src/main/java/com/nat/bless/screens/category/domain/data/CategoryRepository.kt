package com.nat.bless.screens.category.domain.data

import com.nat.bless.base.BaseRequest
import com.nat.bless.base.BaseResponse
import com.nat.bless.base.network.Resource
import com.nat.bless.screens.category.domain.models.CategoryRequest
import com.nat.bless.screens.category.domain.models.CategoryResponse
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    suspend fun getCategoryData(request: BaseRequest<CategoryRequest>): Flow<Resource<BaseResponse<List<CategoryResponse>>>>
}