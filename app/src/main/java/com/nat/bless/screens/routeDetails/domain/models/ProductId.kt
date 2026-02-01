package com.nat.bless.screens.routeDetails.domain.models

import kotlinx.serialization.Serializable

@Serializable

data class ProductId(
    val barcode: String,
    val basic_uom: BasicUom,
    val category_id: CategoryId,
    val id: Int,
    val name: String,
    val reference: String,
    val uom_prices: List<UomPrice>
)