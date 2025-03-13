package com.nat.couriersapp.screens.courierDetails.domain.models


import com.google.gson.annotations.SerializedName

data class DeliveredRequest(
    @SerializedName("Comment")
    val comment: String?,
    @SerializedName("FileBase64")
    val fileBase64: String?,
    @SerializedName("HubName")
    val hubName: String?,
    @SerializedName("IncomingStatusId")
    val incomingStatusId: Int?,
    @SerializedName("LastRefusalReasonId")
    val lastRefusalReasonId: Int?,
    @SerializedName("Latitude")
    val latitude: String?,
    @SerializedName("Longitude")
    val longitude: String?,
    @SerializedName("ReceiverName")
    val receiverName: String?,
    @SerializedName("ReceiverSSN")
    val receiverSSN: String?,
    @SerializedName("RoleId")
    val roleId: String?,
    @SerializedName("UserId")
    val userId: Int?,
    @SerializedName("UserName")
    val userName: String?,
    @SerializedName("WaybillSerials")
    val waybillSerials: List<WaybillSerial>?,
    @SerializedName("ZoneHubId")
    val zoneHubId: Int?
)

data class WaybillSerial(
    @SerializedName("WaybillId")
    val waybillId: Int?,
    @SerializedName("WaybillSerial")
    val waybillSerial: Long?
)