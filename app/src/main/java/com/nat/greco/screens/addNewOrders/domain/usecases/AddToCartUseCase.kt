package com.nat.greco.screens.addNewOrders.domain.usecases

import com.nat.greco.base.BaseRequest
import com.nat.greco.base.BaseResponse
import com.nat.greco.base.network.Resource
import com.nat.greco.screens.addNewOrders.domain.data.AddNewProductRepository
import com.nat.greco.screens.addNewOrders.models.AddToCartRequest
import com.nat.greco.screens.addNewOrders.models.addTocart.AddToCartResponse
import kotlinx.coroutines.flow.Flow

class AddToCartUseCase(
    private val repository: AddNewProductRepository
) {

    suspend fun invoke(request: BaseRequest<AddToCartRequest>): Flow<Resource<AddToCartResponse>> {
        return repository.addToCart(request)
    }
}