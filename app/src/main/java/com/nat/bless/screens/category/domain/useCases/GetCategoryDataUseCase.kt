package com.nat.bless.screens.category.domain.useCases

import com.nat.bless.base.BaseRequest
import com.nat.bless.screens.category.domain.data.CategoryRepository
import com.nat.bless.screens.category.domain.models.CategoryRequest

class GetCategoryDataUseCase(
    private val repository: CategoryRepository
) {

    suspend operator fun invoke(request: BaseRequest<CategoryRequest>) =
        repository.getCategoryData(request)
}