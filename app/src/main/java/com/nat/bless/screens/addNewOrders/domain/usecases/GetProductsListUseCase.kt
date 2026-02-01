package com.nat.bless.screens.addNewOrders.domain.usecases

import com.nat.bless.base.BaseRequest
import com.nat.bless.base.network.Resource
import com.nat.bless.screens.addNewOrders.domain.data.AddNewProductRepository
import com.nat.bless.screens.addNewOrders.models.NewProductRequest
import com.nat.bless.screens.addNewOrders.models.NewProductsResponse
import kotlinx.coroutines.flow.Flow

class GetProductsListUseCase(
    private val repository: AddNewProductRepository
) {

    suspend fun invoke(request: BaseRequest<NewProductRequest>): Flow<Resource<NewProductsResponse>> {
        return repository.getProductsList(request)
    }
}