package com.nat.bless.screens.routeDetails.presentation

import com.nat.bless.screens.home.domain.models.Route

sealed class RouteDetailsEvents {

    data class ClientNameChanged(val name: String) : RouteDetailsEvents()
data object ClearMessage : RouteDetailsEvents()
    data class HomeModelChanged(val model: Route?) : RouteDetailsEvents()
    data class LocationChanged(val lat: String, val lng: String) : RouteDetailsEvents()
    data class ClientSignatureChanged(val image: String) : RouteDetailsEvents()
    data class StartDateChanged(val routeId : Int) : RouteDetailsEvents()
    data class ConfirmRouteReasonsChanged(val routeId : Int, val reasonId : Int) : RouteDetailsEvents()
    data object InitSuccessValue : RouteDetailsEvents()
    data class CancelRouteReasonsChanged(val routeId : Int, val reasonId : Int) : RouteDetailsEvents()
    data class RefusalPickupReasonsChanged(val name : String, val id : Int, val comments: String, val lat : String, val lng : String) : RouteDetailsEvents()
}