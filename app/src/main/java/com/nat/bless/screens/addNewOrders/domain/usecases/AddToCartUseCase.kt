package com.nat.bless.screens.addNewOrders.domain.usecases

import com.nat.bless.base.BaseRequest
import com.nat.bless.base.network.Resource
import com.nat.bless.screens.addNewOrders.domain.data.AddNewProductRepository
import com.nat.bless.screens.addNewOrders.models.AddToCartRequest
import com.nat.bless.screens.addNewOrders.models.addTocart.AddToCartResponse
import kotlinx.coroutines.flow.Flow

class AddToCartUseCase(
    private val repository: AddNewProductRepository
) {

    suspend fun invoke(request: BaseRequest<AddToCartRequest>): Flow<Resource<AddToCartResponse>> {
        return repository.addToCart(request)
    }
}