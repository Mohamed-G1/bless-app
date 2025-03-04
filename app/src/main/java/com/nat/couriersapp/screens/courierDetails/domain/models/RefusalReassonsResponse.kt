package com.nat.couriersapp.screens.courierDetails.domain.models


import com.google.gson.annotations.SerializedName

data class RefusalReasonsResponse(
    @SerializedName("Message")
    val message: String?,
    @SerializedName("Obj")
    val obj: List<RefusalReasonsModel>?,
    @SerializedName("Status")
    val status: Boolean?
)


data class RefusalReasonsModel(
    @SerializedName("IsActive")
    val isActive: Boolean?,
    @SerializedName("RefusalReasonsId")
    val refusalReasonsId: Int?,
    @SerializedName("RefusalReasonsName_Ar")
    val refusalReasonsNameAr: String?,
    @SerializedName("RefusalReasonsName_En")
    val refusalReasonsNameEn: String?,
    @SerializedName("Status")
    val status: Any?,
    @SerializedName("Status_Id")
    val statusId: Int?
)