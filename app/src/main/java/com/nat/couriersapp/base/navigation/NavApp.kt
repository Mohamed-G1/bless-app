package com.nat.couriersapp.base.navigation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.nat.couriersapp.screens.controller.ControllerScreen
import com.nat.couriersapp.screens.courierDetails.presentation.CourierDetailsScreen
import com.nat.couriersapp.screens.courierDetails.presentation.CourierDetailsViewModel
import com.nat.couriersapp.screens.home.domain.models.HomeModel
import com.nat.couriersapp.screens.home.presentation.HomeScreen
import com.nat.couriersapp.screens.home.presentation.HomeViewModel
import com.nat.couriersapp.screens.login.presentation.LoginScreen
import com.nat.couriersapp.screens.login.presentation.LoginViewModel
import com.nat.couriersapp.screens.more.MoreScreen
import com.nat.couriersapp.screens.notifications.NotificationScreen
import com.nat.couriersapp.screens.qrCode.QrCodeScreen
import com.nat.couriersapp.screens.scan.ScanScreen
import com.nat.couriersapp.screens.splash.presentation.SplashScreen
import com.nat.couriersapp.screens.splash.presentation.SplashViewModel
import com.nat.couriersapp.utils.canNavigate
import org.koin.androidx.compose.koinViewModel
import kotlin.reflect.typeOf

/**
 * This file handle the app navigations
 * */
@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun NavApp() {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    var selectedItem by rememberSaveable {
        mutableIntStateOf(0)
    }
    selectedItem = remember(key1 = currentDestination) {
        getSelectedItemAccordingToBackstack(currentDestination)
    }


    Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
        AppBottomNavigation(
            navController = navController
        )


    }) {
        val bottomPadding = it.calculateBottomPadding()

        NavHost(
            navController = navController,
            modifier = Modifier.padding(bottom = bottomPadding),
            startDestination = Destinations.Splash
        ) {

            composable<Destinations.Splash> {
                val viewModel: SplashViewModel = koinViewModel()
                val state by viewModel.state.collectAsStateWithLifecycle()
                SplashScreen(
                    state = state,
                    event = viewModel::onEvent,
                    navigateToNext = {
                        goToNextAfterSplash(navController, state.destination)
                    }
                )
            }

            composable<Destinations.Login> {
                val viewModel: LoginViewModel = koinViewModel()
                val state by viewModel.state.collectAsStateWithLifecycle()
                LoginScreen(
                    state = state,
                    events = viewModel::sendEvent,
                    navigateToHome = {
                        goToHomeScreenFromLogin(navController)
                    }
                )
            }

            composable<Destinations.Home> {
                val viewModel: HomeViewModel = koinViewModel()
                val state by viewModel.state.collectAsStateWithLifecycle()
                HomeScreen(
                    state = state,
                    events = viewModel::sendEvent,
                    onClick = { model ->
                        if (navController.canNavigate)
                            navController.navigate(Destinations.CourierDetails(model))
                    },
                    navigateToNotification = {
                        if (navController.canNavigate)
                            navController.navigate(Destinations.Notification)
                    },
                    signOut = {
                        if (navController.canNavigate)
                            signOutFromHome(navController)
                    }
                )
            }

            composable<Destinations.CourierDetails>(
                typeMap = mapOf(
                    typeOf<HomeModel>() to CustomNavType.HomeModel
                )
            ) {
                val args = it.toRoute<Destinations.CourierDetails>()
                val viewModel: CourierDetailsViewModel = koinViewModel()
                val state by viewModel.state.collectAsStateWithLifecycle()
                CourierDetailsScreen(
                    state = state,
                    events = viewModel::sendEvent,
                    courierModel = args.homeModel,
                    onBackClicked = {
                        if (navController.canNavigate)
                            navController.navigateUp()
                    }
                )


            }

            composable<Destinations.Notification> {
                NotificationScreen(
                    onBackClicked = {
                        if (navController.canNavigate)
                            navController.navigateUp()
                    }
                )
            }

            composable<Destinations.Scan> {
                ScanScreen()
            }

            composable<Destinations.Controller> {
                ControllerScreen()
            }

            composable<Destinations.More> {
                MoreScreen()
            }
        }

    }
}

/** These functions to navigate to the destination screen and remove the previous screen from the back-stack
 * */

private fun goToLoginFromSplashScreen(navController: NavController) {
    navController.navigate(
        Destinations.Login
    ) {
        popUpTo(Destinations.Splash) {
            inclusive = true
        }
    }
}

private fun goToHomeScreen(navController: NavController) {
    navController.navigate(
        Destinations.Home
    ) {
        popUpTo(Destinations.Splash) {
            inclusive = true
        }
    }
}

private fun goToHomeScreenFromLogin(navController: NavController) {
    navController.navigate(
        Destinations.Home
    ) {
        popUpTo(Destinations.Login) {
            inclusive = true
        }
    }
}

private fun signOutFromHome(navController: NavController) {
    navController.navigate(
        Destinations.Login
    ) {
        popUpTo(Destinations.Home) {
            inclusive = true
        }
    }
}
/** This function handle the destination from the splash screen to the appropriate screen
 * */
fun goToNextAfterSplash(navController: NavHostController, destination: Destinations) {
    when (destination) {
        Destinations.Login -> goToLoginFromSplashScreen(navController)
        Destinations.Home -> goToHomeScreen(navController)
        else -> goToLoginFromSplashScreen(navController)
    }
}


private fun getSelectedItemAccordingToBackstack(backstackState: NavDestination?): Int {
    return when (backstackState?.route) {
        BottomScreens.HomeScreen.route.toString() -> 0
        BottomScreens.ScanScreen.route.toString() -> 2
        BottomScreens.ControllerScreen.route.toString() -> 3
        BottomScreens.MoreScreen.route.toString() -> 4
        else -> 0
    }
}





