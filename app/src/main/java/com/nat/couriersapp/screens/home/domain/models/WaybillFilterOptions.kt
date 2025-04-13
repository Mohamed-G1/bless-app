package com.nat.couriersapp.screens.home.domain.models

enum class WaybillFilterOptions(val value: String) {
    AllShipments("All Shipments"),
    NewShipments("New Shipments"),
    InTransit("In Transit"),
    Delivered("Delivered"),
    NotDelivered("Not Delivered")
}