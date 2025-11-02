package com.nat.greco.screens.stocks.peresentation

sealed class StockEvents {
    object GetReturnsStock : StockEvents()
    object TriggerSearchStock : StockEvents()
    object TriggerSearchReturns : StockEvents()
    object GetStockList : StockEvents()
    object ClearStockSearchQuery : StockEvents()
    object ClearReturnsSearchQuery : StockEvents()
    data class SearchStockQueryChanged(val query : String) : StockEvents()
    data class SearchReturnsQueryChanged(val query : String) : StockEvents()
}