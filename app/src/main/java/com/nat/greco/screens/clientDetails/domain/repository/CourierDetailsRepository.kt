package com.nat.greco.screens.clientDetails.domain.repository

import com.nat.greco.base.network.Resource
import com.nat.greco.screens.clientDetails.domain.models.CourierBody
import com.nat.greco.screens.clientDetails.domain.models.DeliveredRequest
import com.nat.greco.screens.clientDetails.domain.models.DeliveredResponse
import com.nat.greco.screens.clientDetails.domain.models.RefusalReasonsResponse
import com.nat.greco.screens.clientDetails.domain.models.StatusNotDeliveredResponse
import kotlinx.coroutines.flow.Flow

interface CourierDetailsRepository {

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
        deliveredRequest : DeliveredRequest
    ): Resource<DeliveredResponse>

    suspend fun getNotDeliveredStatus(
        isActive: Boolean,
        statusTypeId : Int
    ): Flow<Resource<StatusNotDeliveredResponse>>

    suspend fun getNotDeliveredRefusalReasons(
        statusId: Int
    ): Flow<Resource<RefusalReasonsResponse>>
}