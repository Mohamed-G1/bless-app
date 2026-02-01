package com.nat.bless.screens.routeDetails.domain.repository

import com.nat.bless.base.BaseRequest
import com.nat.bless.base.BaseResponse
import com.nat.bless.base.network.Resource
import com.nat.bless.screens.routeDetails.domain.models.ConfirmedAndCancelledReasonsResponse
import com.nat.bless.screens.routeDetails.domain.models.CourierBody
import com.nat.bless.screens.routeDetails.domain.models.DeliveredRequest
import com.nat.bless.screens.routeDetails.domain.models.DeliveredResponse
import com.nat.bless.screens.routeDetails.domain.models.OrderHistoryRequest
import com.nat.bless.screens.routeDetails.domain.models.OrderHistoryResponse
import com.nat.bless.screens.routeDetails.domain.models.RefusalReasonsResponse
import com.nat.bless.screens.routeDetails.domain.models.StatusNotDeliveredResponse
import com.nat.bless.screens.routeDetails.domain.models.ConfirmedAndCancelledRequest
import com.nat.bless.screens.routeDetails.domain.models.TriggeredConfirmedAndCancelledResponse
import kotlinx.coroutines.flow.Flow

interface RouteDetailsRepository {

    suspend fun getConfirmedReasons(
    ): Flow<Resource<ConfirmedAndCancelledReasonsResponse>>

    suspend fun getCancelledReasons(
    ): Flow<Resource<ConfirmedAndCancelledReasonsResponse>>

    suspend fun confirmRoute(
        request: BaseRequest<ConfirmedAndCancelledRequest>
    ): Resource<TriggeredConfirmedAndCancelledResponse>

    suspend fun cancelRoute(
        request: BaseRequest<ConfirmedAndCancelledRequest>
    ): Resource<TriggeredConfirmedAndCancelledResponse>


    suspend fun getOrderHistory(
        request: BaseRequest<OrderHistoryRequest>
    ): Flow<Resource<BaseResponse<List<OrderHistoryResponse>>>>

    suspend fun updateWaybillCourierStatus(
        LastStatusId: Int,
        Comment: String,
        LastRefusalReasonId: Int,
        ActionDate: String,
        UserId: Int,
        UserName: String,
        RoleId: String,
        HubName: String,
        ZoneHubId: Int,
        Latitude: String,
        Longitude: String,
        courierBody: List<CourierBody>
    ): Resource<DeliveredResponse>

    suspend fun updatePickupCourierStatus(
        LastStatusId: Int,
        Comment: String,
        LastRefusalReasonId: Int,
        ActionDate: String,
        UserId: Int,
        UserName: String,
        RoleId: String,
        HubName: String,
        Latitude: String,
        Longitude: String,
        courierBody: List<CourierBody>
    ): Resource<DeliveredResponse>

    suspend fun deliveredCourierWithPOD(
        deliveredRequest: DeliveredRequest
    ): Resource<DeliveredResponse>

    suspend fun getNotDeliveredStatus(
        isActive: Boolean,
        statusTypeId: Int
    ): Flow<Resource<StatusNotDeliveredResponse>>

    suspend fun getNotDeliveredRefusalReasons(
        statusId: Int
    ): Flow<Resource<RefusalReasonsResponse>>
}