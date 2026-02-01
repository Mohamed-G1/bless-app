package com.nat.bless.screens.routeDetails.presentation

import androidx.annotation.StringRes
import com.nat.bless.R
import com.nat.bless.screens.routeDetails.domain.models.RefusalReasonsModel
import com.nat.bless.screens.routeDetails.domain.models.StatusNotDeliveredModel
import com.nat.bless.screens.home.domain.models.Route
import com.nat.bless.screens.routeDetails.domain.models.ConfirmedAndCancelledReasonsResponse

data class RouteDetailsState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val navigateBack: Boolean = false,
    val errorCode: Int? = null,
    val statusNotDelivered: List<StatusNotDeliveredModel> = listOf(),
    val refusalStatusNotDelivered: List<RefusalReasonsModel> = listOf(),
    val confirmedAndCancelledModel: ConfirmedAndCancelledReasonsResponse? = null,
    val refusalName: String = "",
    val statusName: String = "",
    val comments: String = "",
    val statusId: Int = 0,
    val refusalId: Int = 0,
    val confirmId: Int = 0,
    val clientName: String = "",
    val waybillSerial: Long = 0,
    val isValidClientName: Boolean = true,
    val homeModel: Route? = null,
    val image: String = "",
    val lat: String = "",
    val lng: String = "",
    @StringRes val clientNameValidationMessage: Int = R.string.enter_client_name
)
