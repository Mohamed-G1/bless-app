package com.nat.greco.base.navigation

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import com.nat.greco.R
import com.nat.greco.screens.home.domain.models.Route
import kotlinx.serialization.Serializable

/**
 * This class contain the all app destinations routes
 * */
@Serializable
sealed interface Destinations {

    @Serializable
    data object Splash : Destinations

    @Serializable
    data object Login : Destinations

    @Serializable
    data object Home : Destinations

    @Serializable
    data object Clients : Destinations

    @Serializable
    data object Requests : Destinations

    @Serializable
    data object Stock : Destinations

    @Serializable
    data class OrderHistory(val id: Int) : Destinations

    @Serializable
    data class DealingProducts(
        val customerid: Int
    ) : Destinations


    @Serializable
    data object Notification : Destinations

    @Serializable
    data object NewOrderScreen : Destinations

    @Serializable
    data object AddNewClientScreen : Destinations


    @Serializable
    data object Map : Destinations

    @Serializable
    data class OrderDetailsScreen(
        val orderId: Int
    ) : Destinations

    @Serializable
    data class OrderHistoryDetails(
        val id: Int
    )// Now expects the URL-encoded JSON string    )

    @Serializable
    data class Accounts
        (
        val customerid: Int
    ) : Destinations

    @Serializable
    data class Products(
        val customerid: Int
    ) : Destinations

    @Serializable
    data class Contracts(
        val contract: String
    ) : Destinations

    @Serializable
    data class ConfirmOrder(
        val id: Int
    ) : Destinations

    @Serializable
    data class Promotions(
        val customerId: Int
    ) : Destinations

    @Serializable
    data object ReceiveStock : Destinations

    @Serializable
    data class Collect(
        val customerId: Int
    ) : Destinations

    @Serializable
    data class DayDetails(
        val date: String
    ) : Destinations

    @Serializable
    data class Returns(
        val orderId: Int
    ) : Destinations

    @Serializable
    data object ReturnsDetails : Destinations

    @Serializable
    data class DailyReport (
        val date: String
    ): Destinations

    @Serializable
    data class RouteDetails(
        val roues: Route,
        val note: String
    ) : Destinations

    @Serializable
    data class PriceList(
        val customerid: Int
    ) : Destinations

}

// This is the bottom navigation screens
@Serializable
sealed class BottomScreens<T>(@DrawableRes val name: Int, val icon: Int, val route: T) {
    @SuppressLint("ResourceType")
    @Serializable
    data object HomeScreen : BottomScreens<Destinations.Home>(
        name = R.string.home,
        icon = R.drawable.ic_new_home,
        route = Destinations.Home
    )

    @SuppressLint("ResourceType")
    @Serializable
    data object ClientsScreen : BottomScreens<Destinations.Clients>(
        name = R.string.clients,
        icon = R.drawable.ic_clients,
        route = Destinations.Clients
    )

    @SuppressLint("ResourceType")
    @Serializable
    data object RequestsScreen : BottomScreens<Destinations.Requests>(
        name = R.string.requests,
        icon = R.drawable.ic_orders,
        route = Destinations.Requests
    )

    @SuppressLint("ResourceType")
    @Serializable
    data object StockScreen : BottomScreens<Destinations.Stock>(
        name = R.string.stock,
        icon = R.drawable.ic_stock,
        route = Destinations.Stock
    )
}