package com.nat.bless.screens.routeDetails.domain.models

import kotlinx.serialization.Serializable

@Serializable

data class CategoryId(
    val id: Int,
    val name: String
)