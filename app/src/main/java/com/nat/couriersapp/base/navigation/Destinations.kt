package com.nat.couriersapp.base.navigation

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import com.nat.couriersapp.R
import com.nat.couriersapp.screens.home.domain.models.HomeModel
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
    data object Scan : Destinations()

    @Serializable
    data object Controller : Destinations()

    @Serializable
    data object More : Destinations()

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
        icon = R.drawable.ic_home,
        route = Destinations.Home
    )

    @SuppressLint("ResourceType")
    @Serializable
    data object ScanScreen : BottomScreens<Destinations.Scan>(
        name = R.string.scan,
        icon = R.drawable.ic_scan,
        route = Destinations.Scan
    )

    @SuppressLint("ResourceType")
    @Serializable
    data object ControllerScreen : BottomScreens<Destinations.Controller>(
        name = R.string.conroller,
        icon = R.drawable.ic_controller,
        route = Destinations.Controller
    )

    @SuppressLint("ResourceType")
    @Serializable
    data object MoreScreen : BottomScreens<Destinations.More>(
        name = R.string.more,
        icon = R.drawable.ic_more,
        route = Destinations.More
    )
}