package com.nat.greco.screens.courierDetails.domain.usecases

import com.nat.greco.base.network.Resource
import com.nat.greco.screens.courierDetails.domain.models.CourierBody
import com.nat.greco.screens.courierDetails.domain.models.DeliveredResponse
import com.nat.greco.screens.courierDetails.domain.repository.CourierDetailsRepository

class UpdateWaybillCourierStatusUseCase (
    private val repository: CourierDetailsRepository
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