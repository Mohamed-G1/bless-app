package com.nat.couriersapp.screens.courierDetails.domain.usecases

import com.nat.couriersapp.base.network.Resource
import com.nat.couriersapp.screens.courierDetails.domain.models.CourierBody
import com.nat.couriersapp.screens.courierDetails.domain.models.DeliveredResponse
import com.nat.couriersapp.screens.courierDetails.domain.repository.CourierDetailsRepository

class UpdatePickupCourierStatusUseCase (
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