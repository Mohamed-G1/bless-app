package com.nat.couriersapp.screens.courierDetails.data.repository

import android.graphics.Bitmap
import com.nat.couriersapp.base.network.ApiServices
import com.nat.couriersapp.base.network.Resource
import com.nat.couriersapp.base.network.safeApiCall
import com.nat.couriersapp.screens.courierDetails.domain.models.CourierBody
import com.nat.couriersapp.screens.courierDetails.domain.models.RefusalReasonsResponse
import com.nat.couriersapp.screens.courierDetails.domain.models.StatusNotDeliveredResponse
import com.nat.couriersapp.screens.courierDetails.domain.repository.CourierDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class CourierDetailsRepositoryImpl(
    private val apiServices: ApiServices
) : CourierDetailsRepository {
    override suspend fun updateCourierStatus(
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
    ): Resource<Any> {
        return safeApiCall {
            apiServices.updateCourierStatus(
                LastStatusId = LastStatusId,
                Comment = Comment,
                LastRefusalReasonId = LastRefusalReasonId,
                ActionDate = ActionDate,
                UserId = UserId,
                UserName = UserName,
                RoleId = RoleId,
                HubName = HubName,
                ZoneHubId = ZoneHubId,
                Latitude = Latitude,
                Longitude = Longitude,
                courierBody = courierBody
            )
        }
    }

    override suspend fun sendUserSignature(
        file: Bitmap,
        waybill: Int,
        receiverName: String
    ): Resource<Any> {
        return safeApiCall {
            apiServices.sendUserSignature(
                file = file,
                waybill = waybill,
                receiverName = receiverName
            )
        }
    }

    override suspend fun getNotDeliveredStatus(isActive: Boolean): Flow<Resource<StatusNotDeliveredResponse>> =
        flow {
            emit(Resource.Loading)
            val result = safeApiCall {
                apiServices.statusNotDelivered(
                    isActive = isActive
                )
            }
            emit(result)
        }.catch { exception ->
            emit(Resource.Error(message = exception.message.orEmpty()))
        }

    override suspend fun getNotDeliveredRefusalReasons(statusId: Int): Flow<Resource<RefusalReasonsResponse>> =
        flow {
            emit(Resource.Loading)
            val result = safeApiCall {
                apiServices.reasonsByStatusNotDelivered(statusId = statusId)
            }
            emit(result)
        }.catch { exception ->
            emit(Resource.Error(message = exception.message.orEmpty()))
        }
}
