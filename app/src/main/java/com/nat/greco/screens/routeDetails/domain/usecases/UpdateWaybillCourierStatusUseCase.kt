package com.nat.greco.screens.routeDetails.domain.usecases

import com.nat.greco.base.network.Resource
import com.nat.greco.screens.routeDetails.domain.models.CourierBody
import com.nat.greco.screens.routeDetails.domain.models.DeliveredResponse
import com.nat.greco.screens.routeDetails.domain.repository.RouteDetailsRepository

class UpdateWaybillCourierStatusUseCase (
    private val repository: RouteDetailsRepository
) {
    suspend operator fun invoke(
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
    ) : Resource<DeliveredResponse> {
        return repository.updateWaybillCourierStatus(
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