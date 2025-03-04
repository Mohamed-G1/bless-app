package com.nat.couriersapp.screens.courierDetails.domain.usecases

import com.nat.couriersapp.base.network.Resource
import com.nat.couriersapp.screens.courierDetails.domain.models.CourierBody
import com.nat.couriersapp.screens.courierDetails.domain.repository.CourierDetailsRepository

class DeliveredCourierUseCase (
    private val repository: CourierDetailsRepository
) {
    operator suspend fun invoke(
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
    ) : Resource<Any>{
        return repository.updateCourierStatus(
            LastStatusId = 6,
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