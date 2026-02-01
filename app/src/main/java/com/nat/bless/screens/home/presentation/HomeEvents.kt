package com.nat.bless.screens.home.presentation

sealed class HomeEvents {
    data object CallRoutes : HomeEvents()
    data class DataChanged(val date : String) : HomeEvents()
    data class CallWaybillCouriers(val query: String) : HomeEvents()
    data class CallPickupCouriers(val query: String) : HomeEvents()
    data class CallAllCouriers(val query: String) : HomeEvents()
    data object RefreshCouriers: HomeEvents()
    data class WaybillSortTypeClicked(val sort: String) : HomeEvents()
    data class PickupSortTypeClicked(val sort: String) : HomeEvents()
    data class WaybillFilterTypeClicked(val filter: String) : HomeEvents()
    data class PickupFilterTypeClicked(val filter: String) : HomeEvents()
    data class SendFrequentlyLocation(val lat: Double, val long: Double) : HomeEvents()
    data object ResetWaybillSortClicked : HomeEvents()
    data object ResetPickupSortClicked : HomeEvents()
    data object WaybillResetFilterClicked : HomeEvents()
    data object PickupResetFilterClicked : HomeEvents()
    data object clearUser : HomeEvents()
}