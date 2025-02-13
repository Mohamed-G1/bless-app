package com.siad.stayksa.base.navigation

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import com.siad.stayksa.R
import kotlinx.serialization.Serializable

/**
 * This class contain the all app destinations routes
 * */

sealed class Destinations {
    @Serializable
    data object Start : Destinations()

    @Serializable
    data object Splash : Destinations()

    @Serializable
    data object OnBoarding : Destinations()

    @Serializable
    data object SignIn : Destinations()

    @Serializable
    data object ForgetPassword : Destinations()

    @Serializable
     class SignUp(

        ) : Destinations()

    @Serializable
     class Verification: Destinations()

    @Serializable
    data object Home : Destinations()

    @Serializable
    data object Deals : Destinations()

    @Serializable
    data object Booking : Destinations()

    @Serializable
    data object TravelExperience : Destinations()

    @Serializable
    data object Profile : Destinations()

    @Serializable
    data class Daily(val searchKey: String) : Destinations()

    @Serializable
    data class Monthly(val searchKey: String) : Destinations()

    @Serializable
    data class Packages(val searchKey: String) : Destinations()

    @Serializable
     class SearchResult(
    ) : Destinations()

    @Serializable
     class UnitDetails(
    ) : Destinations()



    @Serializable
    data class MyPoints(val currency: String) : Destinations()

    @Serializable
    data object Wallet : Destinations()

    @Serializable
    data object Favorites : Destinations()

    @Serializable
    data object TravelStories : Destinations()

    @Serializable
    data object MyReviews : Destinations()

    @Serializable
    data object AddUserExperience : Destinations()



    @Serializable
    data object NotificationsSettings : Destinations()

    @Serializable
    data object AboutUs : Destinations()

    @Serializable
    data object ContactUs : Destinations()

    @Serializable
    data object Terms : Destinations()

    @Serializable
    data object Policy : Destinations()

    @Serializable
    data class BookingResult(
        val bookingNumber: String,
        val earnedPoints: Int,
        val dateFrom: String,
        val dateTo: String,
        val guests: Int,
        val total: Int,
        val paymentMethod: String,
    ) : Destinations()



}

// This is the bottom navigation screens
@Serializable
sealed class BottomScreens<T>(@DrawableRes val name: Int, val icon: Int, val route: T) {
    @SuppressLint("ResourceType")
    @Serializable
    data object HomeScreen : BottomScreens<Destinations.Home>(
        name = R.string.explore,
        icon = R.drawable.ic_launcher_background,
        route = Destinations.Home
    )

    @SuppressLint("ResourceType")
    @Serializable
    data object BookingScreen : BottomScreens<Destinations.Booking>(
        name = R.string.booking,
        icon = R.drawable.ic_launcher_background,
        route = Destinations.Booking
    )

    @SuppressLint("ResourceType")
    @Serializable
    data object ExperienceScreen : BottomScreens<Destinations.TravelExperience>(
        name = R.string.experinces,
        icon = R.drawable.ic_launcher_background,
        route = Destinations.TravelExperience
    )

    @SuppressLint("ResourceType")
    @Serializable
    data object ProfileScreen : BottomScreens<Destinations.Profile>(
        name = R.string.profile, icon = R.drawable.ic_launcher_background, route = Destinations.Profile
    )
}