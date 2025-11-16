package com.nat.greco.base.navigation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.nat.greco.screens.routeDetails.domain.models.OrderHistoryResponse
import com.nat.greco.screens.routeDetails.domain.models.OrderHistoryLine
import com.nat.greco.screens.home.domain.models.Route
import com.nat.greco.screens.orders.domain.models.OrdersResponse
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

// one Json instance (tweak if you need)
private val NavJson = Json {
    ignoreUnknownKeys = true
    encodeDefaults = true
}

object OrderHistoryNavType : NavType<OrderHistoryResponse>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): OrderHistoryResponse? =
        bundle.getString(key)
            ?.let { NavJson.decodeFromString(OrderHistoryResponse.serializer(), it) }

    override fun parseValue(value: String): OrderHistoryResponse =
        NavJson.decodeFromString(OrderHistoryResponse.serializer(), Uri.decode(value))

    override fun serializeAsValue(value: OrderHistoryResponse): String =
        Uri.encode(NavJson.encodeToString(OrderHistoryResponse.serializer(), value))

    override fun put(bundle: Bundle, key: String, value: OrderHistoryResponse) {
        bundle.putString(key, NavJson.encodeToString(OrderHistoryResponse.serializer(), value))
    }
}

@Serializable
object CustomNavType {
    val HomeModel = object : NavType<Route>(
        isNullableAllowed = true
    ) {
        override fun get(bundle: Bundle, key: String): Route? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): Route {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun serializeAsValue(value: Route): String {
            return Uri.encode(Json.encodeToString(value = value))
        }

        override fun put(bundle: Bundle, key: String, value: Route) {
            bundle.putString(key, Json.encodeToString(value = value))
        }
    }

    val OrdersModel = object : NavType<OrdersResponse>(
        isNullableAllowed = true
    ) {
        override fun get(bundle: Bundle, key: String): OrdersResponse? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): OrdersResponse {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun serializeAsValue(value: OrdersResponse): String {
            return Uri.encode(Json.encodeToString(value = value))
        }

        override fun put(bundle: Bundle, key: String, value: OrdersResponse) {
            bundle.putString(key, Json.encodeToString(value = value))
        }
    }


    // --- Define your CustomNavType properly ---
    val OrderLineListType = object : NavType<List<OrderHistoryLine>>(isNullableAllowed = true) {
        override fun get(bundle: Bundle, key: String): List<OrderHistoryLine>? {
            return bundle.getString(key)?.let { Json.decodeFromString(it) }
        }

        override fun parseValue(value: String): List<OrderHistoryLine> {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun serializeAsValue(value: List<OrderHistoryLine>): String {
            return Uri.encode(Json.encodeToString(value))
        }

        override fun put(bundle: Bundle, key: String, value: List<OrderHistoryLine>) {
            bundle.putString(key, Json.encodeToString(value))
        }
    }

}


