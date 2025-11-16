package com.nat.greco.screens.returnsScreen

sealed class ReturnsEvents {

    data class OrderIdChanged(val id: Int) : ReturnsEvents()
    data class CounterChanged(val lines : List<Lines>) : ReturnsEvents()
    data object ReturnProducts : ReturnsEvents()

}