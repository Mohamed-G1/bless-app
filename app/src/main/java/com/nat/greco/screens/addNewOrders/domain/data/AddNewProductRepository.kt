package com.nat.greco.screens.addNewOrders.domain.data

import com.nat.greco.base.BaseRequest
import com.nat.greco.base.BaseResponse
import com.nat.greco.base.network.Resource
import com.nat.greco.screens.addNewOrders.models.AddToCartRequest
import com.nat.greco.screens.addNewOrders.models.NewProductRequest
import com.nat.greco.screens.addNewOrders.models.NewProductsResponse
import com.nat.greco.screens.addNewOrders.models.addTocart.AddToCartResponse
import kotlinx.coroutines.flow.Flow

interface AddNewProductRepository {
    suspend fun getProductsList(
        request: BaseRequest<NewProductRequest>
    ): Flow<Resource<NewProductsResponse>>

    suspend fun addToCart(
        request: BaseRequest<AddToCartRequest>
    ): Flow<Resource<AddToCartResponse>>
}