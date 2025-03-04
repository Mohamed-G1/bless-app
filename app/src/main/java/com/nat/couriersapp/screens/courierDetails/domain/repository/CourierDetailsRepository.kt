package com.nat.couriersapp.screens.courierDetails.domain.repository

import android.graphics.Bitmap
import com.nat.couriersapp.base.network.Resource
import com.nat.couriersapp.screens.courierDetails.domain.models.CourierBody
import com.nat.couriersapp.screens.courierDetails.domain.models.RefusalReasonsResponse
import com.nat.couriersapp.screens.courierDetails.domain.models.StatusNotDeliveredResponse
import kotlinx.coroutines.flow.Flow

interface CourierDetailsRepository {

    suspend fun updateCourierStatus(
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
    ): Resource<Any>

    suspend fun sendUserSignature(
        file: Bitmap,
        waybill: Int,
        receiverName: String,
    ): Resource<Any>

    suspend fun getNotDeliveredStatus(
        isActive: Boolean
    ): Flow<Resource<StatusNotDeliveredResponse>>

    suspend fun getNotDeliveredRefusalReasons(
        statusId: Int
    ): Flow<Resource<RefusalReasonsResponse>>
}