package com.nat.couriersapp.screens.home.presentation

import com.nat.couriersapp.screens.home.domain.models.HomeModel
import com.nat.couriersapp.screens.home.domain.models.HomeResponse

data class HomeState(
    val isLoading: Boolean = false,
    val model: HomeResponse? = null,
    val homeList: List<HomeModel> = emptyList(),
    val originalHomeList: List<HomeModel> = emptyList(),
    val errorMessage: String? = null,
    val errorCode: Int? = null,
    val userName: String? = null,
    val waybillSortType: String? = null,
    val pickupSortType: String? = null,
    val waybillFilterType: String? = null,
    val pickupFilterType: String? = null,
    val lat: String? = null,
    val lng: String? = null,

    val date: String? = null,
    val courierType: String? = null
)
