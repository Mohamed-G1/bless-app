package com.nat.bless.screens.home.presentation

import com.nat.bless.screens.home.domain.models.HomeResponse

data class HomeState(
    val isLoading: Boolean = false,
    val model: HomeResponse? = null,
    val errorMessage: String? = null,
    val snackBarMessage: String? = null,
    val userName: String? = null,
    val lat: String? = null,
    val lng: String? = null,
    val date: String? = null,
)

