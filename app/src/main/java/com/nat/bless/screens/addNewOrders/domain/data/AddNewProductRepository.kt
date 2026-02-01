package com.nat.bless.screens.addNewOrders.domain.data

import com.nat.bless.base.BaseRequest
import com.nat.bless.base.network.Resource
import com.nat.bless.screens.addNewOrders.models.AddToCartRequest
import com.nat.bless.screens.addNewOrders.models.NewProductRequest
import com.nat.bless.screens.addNewOrders.models.NewProductsResponse
import com.nat.bless.screens.addNewOrders.models.addTocart.AddToCartResponse
import kotlinx.coroutines.flow.Flow

interface AddNewProductRepository {
    suspend fun getProductsList(
        request: BaseRequest<NewProductRequest>
    ): Flow<Resource<NewProductsResponse>>

    suspend fun addToCart(
        request: BaseRequest<AddToCartRequest>
    ): Flow<Resource<AddToCartResponse>>
}