package com.nat.greco.base.navigation

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import com.nat.greco.R
import com.nat.greco.screens.home.domain.models.HomeModel
import kotlinx.serialization.Serializable

/**
 * This class contain the all app destinations routes
 * */

sealed class Destinations {

    @Serializable
    data object Splash : Destinations()

    @Serializable
    data object Login : Destinations()

    @Serializable
    data object Home : Destinations()

    @Serializable
    data object Clients : Destinations()

    @Serializable
    data object Requests : Destinations()

    @Serializable
    data object Stock : Destinations()

    @Serializable
    data object LastOrders : Destinations()

    @Serializable
    data object ProductsList : Destinations()


    @Serializable
    data object Notification : Destinations()
    @Serializable
    data object NewRequestScreen : Destinations()
    @Serializable
    data object AddNewClientScreen : Destinations()
    @Serializable
    data object OrderDetailsScreen : Destinations()
    @Serializable
    data object LastOrdersDetails : Destinations()
    @Serializable
    data object Accounts : Destinations()

    @Serializable
    data object Products : Destinations()
    @Serializable
    data object ProductDetails : Destinations()

    @Serializable
    data object Offers : Destinations()
    @Serializable
    data object ReceiveStock : Destinations()
    @Serializable
    data object EndDay : Destinations()
    @Serializable
    data object Returns : Destinations()
    @Serializable
    data object ReturnsDetails : Destinations()

    @Serializable
    data class CourierDetails(
        val homeModel: HomeModel
    ) : Destinations()

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