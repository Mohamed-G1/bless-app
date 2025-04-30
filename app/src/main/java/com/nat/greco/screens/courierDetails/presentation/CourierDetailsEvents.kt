package com.nat.greco.screens.courierDetails.presentation

import com.nat.greco.screens.home.domain.models.HomeModel

sealed class CourierDetailsEvents {

    data class ClientNameChanged(val name: String) : CourierDetailsEvents()
    data class BarCodeScannerValue(val code: Long) : CourierDetailsEvents()
    data class PickupBarCodeScannerValue(val code: Long) : CourierDetailsEvents()
    data object TriggerWaybillDeliveredApi : CourierDetailsEvents()
    data object TriggerPickupDeliveredApi : CourierDetailsEvents()
    data object TriggerPickupNotDeliveredApi : CourierDetailsEvents()
    data class HomeModelChanged(val model: HomeModel?) : CourierDetailsEvents()
    data class LocationChanged(val lat: String, val lng: String) : CourierDetailsEvents()
    data class ClientSignatureChanged(val image: String) : CourierDetailsEvents()
    data object NotDeliveredStatus : CourierDetailsEvents()
    data class StatusChanged( val name : String , val id : Int) : CourierDetailsEvents()
    data class RefusalWaybillReasonsChanged(val name : String, val id : Int, val comments: String, val lat : String, val lng : String) : CourierDetailsEvents()
    data class RefusalPickupReasonsChanged(val name : String, val id : Int, val comments: String, val lat : String, val lng : String) : CourierDetailsEvents()
}