package com.nat.couriersapp.screens.courierDetails.domain.repository

import com.nat.couriersapp.base.network.Resource
import com.nat.couriersapp.screens.courierDetails.domain.models.CourierBody
import com.nat.couriersapp.screens.courierDetails.domain.models.DeliveredRequest
import com.nat.couriersapp.screens.courierDetails.domain.models.DeliveredResponse
import com.nat.couriersapp.screens.courierDetails.domain.models.RefusalReasonsResponse
import com.nat.couriersapp.screens.courierDetails.domain.models.StatusNotDeliveredResponse
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
        isActive: Boolean
    ): Flow<Resource<StatusNotDeliveredResponse>>

    suspend fun getNotDeliveredRefusalReasons(
        statusId: Int
    ): Flow<Resource<RefusalReasonsResponse>>
}