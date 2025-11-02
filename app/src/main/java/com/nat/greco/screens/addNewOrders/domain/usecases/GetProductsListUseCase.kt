package com.nat.greco.screens.addNewOrders.domain.usecases

import com.nat.greco.base.BaseRequest
import com.nat.greco.base.network.Resource
import com.nat.greco.screens.addNewOrders.domain.data.AddNewProductRepository
import com.nat.greco.screens.addNewOrders.models.NewProductRequest
import com.nat.greco.screens.addNewOrders.models.NewProductsResponse
import kotlinx.coroutines.flow.Flow

class GetProductsListUseCase(
    private val repository: AddNewProductRepository
) {

    suspend fun invoke(request: BaseRequest<NewProductRequest>): Flow<Resource<NewProductsResponse>> {
        return repository.getProductsList(request)
    }
}