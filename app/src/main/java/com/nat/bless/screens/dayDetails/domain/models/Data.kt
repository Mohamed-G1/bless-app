package com.nat.bless.screens.dayDetails.domain.models

data class Data(
    val customer_details: List<CustomerDetail>,
    val not_viste_count: Int,
    val viste_count: Int
)