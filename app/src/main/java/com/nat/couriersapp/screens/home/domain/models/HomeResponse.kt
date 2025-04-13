package com.nat.couriersapp.screens.home.domain.models


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

data class HomeResponse(
    @SerializedName("Message")
    val message: String?,
    @SerializedName("Obj")
    val obj: Obj?,
    @SerializedName("Status")
    val status: Boolean?
)

data class Obj(
    @SerializedName("currentPage")
    val currentPage: Int?,
    @SerializedName("data")
    val `data`: List<HomeModel>?,
    @SerializedName("total")
    val total: Int?
)


@Serializable
data class HomeModel(
    @SerializedName("ActualWeight")
    val actualWeight: Double?,
    @SerializedName("CollectCharges")
    val collectCharges: Double?,
    @SerializedName("CollectedBy")
    val collectedBy: String?,
    @SerializedName("CollectedDate")
    val collectedDate: String?,
    @SerializedName("ConsigneeCode")
    val consigneeCode: String?,
    @SerializedName("ConsigneeDestinationAddress")
    val consigneeDestinationAddress: String?,
    @SerializedName("ConsigneeId")
    val consigneeId: Int?,
    @SerializedName("ConsigneeMobile")
    val consigneeMobile: String?,
    @SerializedName("ConsigneeName")
    val consigneeName: String?,
    @SerializedName("ConsigneePhone")
    val consigneePhone: String?,
    @SerializedName("ConsigneeReference")
    val consigneeReference: String?,
    @SerializedName("CreatedBy")
    val createdBy: Int?,
    @SerializedName("CreatedByName")
    val createdByName: String?,
    @SerializedName("CreationDate")
    val creationDate: String?,
    @SerializedName("CreationTime")
    val creationTime: String?,
    @SerializedName("DepositDate")
    val depositDate: String?,
    @SerializedName("DepositedBy")
    val depositedBy: String?,
    @SerializedName("DestinationCityName")
    val destinationCityName: String?,
    @SerializedName("DestinationCountryName")
    val destinationCountryName: String?,
    @SerializedName("DestinationThreeLetterCode")
    val destinationThreeLetterCode: String?,
    @SerializedName("DestinationZipCode")
    val destinationZipCode: String?,
    @SerializedName("HubId")
    val hubId: Int?,
    @SerializedName("HubName")
    val hubName: String?,
    @SerializedName("IsCheckedToConfirm")
    val isCheckedToConfirm: Boolean?,
    @SerializedName("IsCollected")
    val isCollected: Boolean?,
    @SerializedName("IsDeliverd")
    val isDeliverd: Boolean?,
    @SerializedName("IsDeposit")
    val isDeposit: Boolean?,
    @SerializedName("IsRecievedByCourier")
    val isRecievedByCourier: String?,
    @SerializedName("LastStatusActionDate")
    val lastStatusActionDate: String?,
    @SerializedName("LastStatusComment")
    val lastStatusComment: String?,
    @SerializedName("LastStatusId")
    val lastStatusId: Int?,
    @SerializedName("LastStatusName")
    val lastStatusName: String?,
    @SerializedName("LastStatusUpdatedByName")
    val lastStatusUpdatedByName: String?,
    @SerializedName("LastStatusUpdatedDate")
    val lastStatusUpdatedDate: String?,
    @SerializedName("LastStatusUpdatedTime")
    val lastStatusUpdatedTime: String?,
    @SerializedName("OriginCity")
    val originCity: String?,
    @SerializedName("OriginCountry")
    val originCountry: String?,
    @SerializedName("OriginThreeLetterCode")
    val originThreeLetterCode: String?,
    @SerializedName("OriginZipCode")
    val originZipCode: String?,
    @SerializedName("Remarks")
    val remarks: String?,
    @SerializedName("RunnerSheetDetailsId")
    val runnerSheetDetailsId: Int?,
    @SerializedName("ShipperCode")
    val shipperCode: String?,
    @SerializedName("ShipperContactName")
    val shipperContactName: String?,
    @SerializedName("ShipperId")
    val shipperId: Int?,
    @SerializedName("ShipperName")
    val shipperName: String?,
    @SerializedName("ShipperOriginAddress")
    val shipperOriginAddress: String?,
    @SerializedName("ShipperPhone")
    val shipperPhone: String?,
    @SerializedName("ShipperReference")
    val shipperReference: String?,
    @SerializedName("TransitTime")
    val transitTime: String?,
    @SerializedName("WaybillCODValue")
    val waybillCODValue: Double?,
    @SerializedName("WaybillComment")
    val waybillComment: String?,
    @SerializedName("WaybillCondition")
    val waybillCondition: String?,
    @SerializedName("WaybillId")
    val waybillId: Int?,
    @SerializedName("WaybillPickupDate")
    val waybillPickupDate: String?,
    @SerializedName("WaybillPieces")
    val waybillPieces: Int?,
    @SerializedName("WaybillSerial")
    val waybillSerial: Long?,
    @SerializedName("Weight")
    val weight: Double?,

    val courierType : String
)