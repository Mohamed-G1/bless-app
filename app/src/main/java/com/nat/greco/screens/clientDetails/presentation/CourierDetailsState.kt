package com.nat.greco.screens.clientDetails.presentation

import androidx.annotation.StringRes
import com.nat.greco.R
import com.nat.greco.screens.clientDetails.domain.models.RefusalReasonsModel
import com.nat.greco.screens.clientDetails.domain.models.StatusNotDeliveredModel
import com.nat.greco.screens.home.domain.models.HomeModel

data class CourierDetailsState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val navigateBack: Boolean = false,
    val errorCode: Int? = null,
    val statusNotDelivered: List<StatusNotDeliveredModel> = listOf(),
    val refusalStatusNotDelivered: List<RefusalReasonsModel> = listOf(),
    val refusalName: String = "",
    val statusName: String = "",
    val comments: String = "",
    val statusId: Int = 0,
    val refusalId: Int = 0,
    val clientName: String = "",
    val waybillSerial: Long = 0,
    val isValidClientName: Boolean = true,
    val homeModel: HomeModel? = null,
    val image: String = "",
    val lat: String = "",
    val lng: String = "",
    @StringRes val clientNameValidationMessage: Int = R.string.enter_client_name
)
