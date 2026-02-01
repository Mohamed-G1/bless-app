package com.nat.bless.screens.routeDetails.domain.models

import kotlinx.serialization.Serializable

@Serializable

data class BasicUom(
    val uom_id: Int,
    val uom_name: String
)