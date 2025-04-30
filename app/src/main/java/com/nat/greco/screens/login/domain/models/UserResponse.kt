package com.nat.greco.screens.login.domain.models


import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("Message")
    val message: String?,
    @SerializedName("Status")
    val status: Boolean?,
    @SerializedName("Obj")
    val obj: UserModel?
)

data class UserModel(
    @SerializedName("EmployeeId")
    val employeeId: Int?,
    @SerializedName("GeneralTreasuryHubAccountId")
    val generalTreasuryHubAccountId: Int?,
    @SerializedName("HubCode")
    val hubCode: String?,
    @SerializedName("HubId")
    val hubId: Int?,
    @SerializedName("HubName")
    val hubName: String?,
    @SerializedName("Id")
    val id: String?,
    @SerializedName("IsFullDayShift")
    val isFullDayShift: Boolean?,
    @SerializedName("Language")
    val language: String?,
    @SerializedName("MainHubAccountId")
    val mainHubAccountId: Int?,
    @SerializedName("MainHubId")
    val mainHubId: Int?,
    @SerializedName("MainManagmentHubAccountId")
    val mainManagmentHubAccountId: Int?,
    @SerializedName("MainOperationHubAccountId")
    val mainOperationHubAccountId: Int?,
    @SerializedName("MainOperationHubId")
    val mainOperationHubId: Int?,
    @SerializedName("RoleId")
    val roleId: Int?,
    @SerializedName("ShipperId")
    val shipperId: Any?,
    @SerializedName("Token")
    val token: String?,
    @SerializedName("UserId")
    val userId: Int?,
    @SerializedName("UserName")
    val userName: String?,
    @SerializedName("UserPhotoPath")
    val userPhotoPath: Any?,
    @SerializedName("userroles")
    val userroles: String?,

    @SerializedName("DeliverStatusId")
    val DeliverStatusId: String,
    @SerializedName("DeliverReasonId")
    val DeliverReasonId: String,
    @SerializedName("InTransitStatusId")
    val InTransitStatusId: String,
    @SerializedName("InTransitReasonId")
    val InTransitReasonId: String,
    @SerializedName("CourierPickedupStatusId")
    val CourierPickedupStatusId: String,
    @SerializedName("CourierPickedupReasonId")
    val CourierPickedupReasonId: String,
)