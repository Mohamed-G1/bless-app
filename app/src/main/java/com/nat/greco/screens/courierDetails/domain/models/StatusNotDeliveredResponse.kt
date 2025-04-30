package com.nat.greco.screens.courierDetails.domain.models


import com.google.gson.annotations.SerializedName

data class StatusNotDeliveredResponse(
    @SerializedName("Message")
    val message: String?,
    @SerializedName("Obj")
    val obj: List<StatusNotDeliveredModel>?,
    @SerializedName("Status")
    val status: Boolean?
)

data class StatusNotDeliveredModel(
    @SerializedName("IsActive")
    val isActive: Boolean?,
    @SerializedName("IsCourierStatus")
    val isCourierStatus: Boolean?,
    @SerializedName("IsInternal")
    val isInternal: Boolean?,
    @SerializedName("Status_Id")
    val statusId: Int?,
    @SerializedName("Status_Name_Ar")
    val statusNameAr: String?,
    @SerializedName("Status_Name_En")
    val statusNameEn: String?,
    @SerializedName("StatusType")
    val statusType: Any?,
    @SerializedName("StatusTypeId")
    val statusTypeId: Int?,
    @SerializedName("WaybillStatuses")
    val waybillStatuses: List<Any?>?
)