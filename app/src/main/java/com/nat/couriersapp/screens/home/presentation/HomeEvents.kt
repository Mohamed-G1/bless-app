package com.nat.couriersapp.screens.home.presentation

sealed class HomeEvents {
    data class CallAllCouriers(val query: String) : HomeEvents()
    data class SortTypeClicked(val sort: String) : HomeEvents()
    data class FilterTypeClicked(val filter: String) : HomeEvents()
    data class SendFrequentlyLocation(val lat: Double, val long: Double) : HomeEvents()
    data object ResetSortClicked : HomeEvents()
    data object ResetFilterClicked : HomeEvents()
}