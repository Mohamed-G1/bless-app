package com.nat.greco.screens.collect.presenation

sealed class CollectEvents {
    data class CustomerIdChanged(val id: Int) : CollectEvents()
    data class JournalIdChanged(val id: Int) : CollectEvents()
    data class AmountChanged(val amount: Double) : CollectEvents()
    data class NoteChanged(val note: String) : CollectEvents()
    data object CreatePayment : CollectEvents()
}