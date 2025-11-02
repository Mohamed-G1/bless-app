package com.nat.greco.screens.stocks.models

data class SearchRequest(
    val location: String,
    val query: String,
    val token: String
)