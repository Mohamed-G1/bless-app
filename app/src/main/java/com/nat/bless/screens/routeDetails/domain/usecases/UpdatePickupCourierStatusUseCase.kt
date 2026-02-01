package com.nat.bless.screens.routeDetails.domain.usecases

import com.nat.bless.base.network.Resource
import com.nat.bless.screens.routeDetails.domain.models.CourierBody
import com.nat.bless.screens.routeDetails.domain.models.DeliveredResponse
import com.nat.bless.screens.routeDetails.domain.repository.RouteDetailsRepository

class UpdatePickupCourierStatusUseCase (
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
        Latitude: String,
        Longitude: String,
        courierBody: List<CourierBody>
    ) : Resource<DeliveredResponse> {
        return repository.updatePickupCourierStatus(
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