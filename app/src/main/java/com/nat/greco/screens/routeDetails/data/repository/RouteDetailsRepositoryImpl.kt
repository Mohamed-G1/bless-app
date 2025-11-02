package com.nat.greco.screens.routeDetails.data.repository

import com.nat.greco.base.BaseRequest
import com.nat.greco.base.BaseResponse
import com.nat.greco.base.network.ApiServices
import com.nat.greco.base.network.Resource
import com.nat.greco.base.network.safeApiCall
import com.nat.greco.screens.routeDetails.domain.models.ConfirmedAndCancelledReasonsResponse
import com.nat.greco.screens.routeDetails.domain.models.CourierBody
import com.nat.greco.screens.routeDetails.domain.models.DeliveredRequest
import com.nat.greco.screens.routeDetails.domain.models.DeliveredResponse
import com.nat.greco.screens.routeDetails.domain.models.OrderHistoryRequest
import com.nat.greco.screens.routeDetails.domain.models.OrderHistoryResponse
import com.nat.greco.screens.routeDetails.domain.models.RefusalReasonsResponse
import com.nat.greco.screens.routeDetails.domain.models.StatusNotDeliveredResponse
import com.nat.greco.screens.routeDetails.domain.models.ConfirmedAndCancelledRequest
import com.nat.greco.screens.routeDetails.domain.models.TriggeredConfirmedAndCancelledResponse
import com.nat.greco.screens.routeDetails.domain.repository.RouteDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class RouteDetailsRepositoryImpl(
    private val apiServices: ApiServices
) : RouteDetailsRepository {
    override suspend fun getConfirmedReasons(): Flow<Resource<ConfirmedAndCancelledReasonsResponse>> =
        flow {
            emit(Resource.Loading)
            val result = safeApiCall {
                apiServices.getConfirmedReasons()
            }
            emit(result)
        }.catch { exception ->
            emit(Resource.Error(message = exception.message.orEmpty()))
        }

    override suspend fun getCancelledReasons(): Flow<Resource<ConfirmedAndCancelledReasonsResponse>> =
        flow {
            emit(Resource.Loading)
            val result = safeApiCall {
                apiServices.getCancelledReasons()
            }
            emit(result)
        }.catch { exception ->
            emit(Resource.Error(message = exception.message.orEmpty()))
        }


    override suspend fun confirmRoute(request: BaseRequest<ConfirmedAndCancelledRequest>): Resource<TriggeredConfirmedAndCancelledResponse> {
        return safeApiCall {
            apiServices.confirmRoute(request = request)
        }
    }

    override suspend fun cancelRoute(request: BaseRequest<ConfirmedAndCancelledRequest>): Resource<TriggeredConfirmedAndCancelledResponse> {
        return safeApiCall {
            apiServices.cancelledRoute(request = request)
        }
    }

    override suspend fun getOrderHistory(request: BaseRequest<OrderHistoryRequest>):
            Flow<Resource<BaseResponse<List<OrderHistoryResponse>>>> = flow {
        emit(Resource.Loading)
        val result = safeApiCall {
            apiServices.getOrderHistory(routeRequest = request)
        }
        emit(result)
    }.catch { exception ->
        emit(Resource.Error(message = exception.message.orEmpty()))
    }

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


    override suspend fun getNotDeliveredStatus(
        isActive: Boolean,
        statusTypeId: Int
    ): Flow<Resource<StatusNotDeliveredResponse>> =
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
