package com.nat.greco.screens.clientDetails.data.repository

import com.nat.greco.base.network.ApiServices
import com.nat.greco.base.network.Resource
import com.nat.greco.base.network.safeApiCall
import com.nat.greco.screens.clientDetails.domain.models.CourierBody
import com.nat.greco.screens.clientDetails.domain.models.DeliveredRequest
import com.nat.greco.screens.clientDetails.domain.models.DeliveredResponse
import com.nat.greco.screens.clientDetails.domain.models.RefusalReasonsResponse
import com.nat.greco.screens.clientDetails.domain.models.StatusNotDeliveredResponse
import com.nat.greco.screens.clientDetails.domain.repository.CourierDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class CourierDetailsRepositoryImpl(
    private val apiServices: ApiServices
) : CourierDetailsRepository {
    override suspend fun updateWaybillCourierStatus(
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
    ): Resource<DeliveredResponse> {
        return safeApiCall {
            apiServices.updateWaybillCourierStatus(
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

    override suspend fun updatePickupCourierStatus(
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
    ): Resource<DeliveredResponse> {
        return safeApiCall {
            apiServices.updatePickupCourierStatus(
                LastStatusId = LastStatusId,
                Comment = Comment,
                LastRefusalReasonId = LastRefusalReasonId,
                ActionDate = ActionDate,
                UserId = UserId,
                UserName = UserName,
                RoleId = RoleId,
                HubName = HubName,
                Latitude = Latitude,
                Longitude = Longitude,
                courierBody = courierBody
            )
        }
    }

    override suspend fun deliveredCourierWithPOD(
        deliveredRequest: DeliveredRequest
    ): Resource<DeliveredResponse> {
        return safeApiCall {
            apiServices.deliveredCourierWithPOD(
                deliveredRequest = deliveredRequest
            )
        }
    }


    override suspend fun getNotDeliveredStatus(isActive: Boolean,statusTypeId : Int): Flow<Resource<StatusNotDeliveredResponse>> =
        flow {
            emit(Resource.Loading)
            val result = safeApiCall {
                apiServices.statusNotDelivered(
                    isActive = isActive,
                    statusTypeId = statusTypeId
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
