package com.nat.couriersapp.screens.courierDetails.presentation

import android.graphics.Bitmap
import com.nat.couriersapp.screens.home.domain.models.HomeModel

sealed class CourierDetailsEvents {

    data class ClientNameChanged(val name: String) : CourierDetailsEvents()
    data object TriggerUserSignatureApi : CourierDetailsEvents()
    data class HomeModelChanged(val model: HomeModel?) : CourierDetailsEvents()
    data class LocationChanged(val lat: String, val lng: String) : CourierDetailsEvents()
    data class ClientSignatureChanged(val bimap: Bitmap) : CourierDetailsEvents()
    data object NotDeliveredStatus : CourierDetailsEvents()
    data class StatusChanged( val name : String , val id : Int) : CourierDetailsEvents()
    data class RefusalReasonsChanged( val name : String , val id : Int, val comments: String, val lat : String, val lng : String) : CourierDetailsEvents()
}