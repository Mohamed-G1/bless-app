package com.nat.couriersapp.screens.courierDetails.domain.models


import com.google.gson.annotations.SerializedName

data class DeliveredResponse(
    @SerializedName("Message")
    val message: String?,
    @SerializedName("Obj")
    val obj: Any?,
    @SerializedName("Status")
    val status: Boolean?
)