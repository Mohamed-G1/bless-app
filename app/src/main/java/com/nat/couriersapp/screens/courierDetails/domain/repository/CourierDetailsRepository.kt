package com.nat.couriersapp.screens.courierDetails.domain.repository

import android.graphics.Bitmap
import com.nat.couriersapp.base.network.Resource
import com.nat.couriersapp.screens.courierDetails.domain.models.CourierBody
import com.nat.couriersapp.screens.courierDetails.domain.models.DeliveredRequest
import com.nat.couriersapp.screens.courierDetails.domain.models.DeliveredResponse
import com.nat.couriersapp.screens.courierDetails.domain.models.RefusalReasonsResponse
import com.nat.couriersapp.screens.courierDetails.domain.models.StatusNotDeliveredResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

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
    ): Resource<DeliveredResponse>

    suspend fun deliveredCourier(
        deliveredRequest : DeliveredRequest
    ): Resource<DeliveredResponse>

    suspend fun getNotDeliveredStatus(
        isActive: Boolean
    ): Flow<Resource<StatusNotDeliveredResponse>>

    suspend fun getNotDeliveredRefusalReasons(
        statusId: Int
    ): Flow<Resource<RefusalReasonsResponse>>
}