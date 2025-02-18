package com.nat.couriersapp.base.network.response

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("Message")
    val message: String?,
    @SerializedName("Status")
    val status: Boolean?,
    @SerializedName("Obj")
    val obj: T?
)