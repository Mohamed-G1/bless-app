package com.nat.couriersapp.base.navigation

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
import com.nat.couriersapp.screens.splash.presentation.SplashScreen
import com.nat.couriersapp.screens.splash.presentation.SplashViewModel
import org.koin.androidx.compose.koinViewModel

/**
 * This file handle the app navigations
 * */
@Composable
fun NavApp(){
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
                    navigateToNext = {
                        goToNextAfterSplash(navController, state.destination)
                    }
                )
            }

            composable<Destinations.Home> {

            }
        }

    }
}

/** These functions to navigate to the destination screen and remove the previous screen from the back-stack
 * */

private fun goToSignInFromSplashScreen(navController: NavController) {
    navController.navigate(
        Destinations.SignIn
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

/** This function handle the destination from the splash screen to the appropriate screen
 * */
fun goToNextAfterSplash(navController: NavHostController, destination: Destinations) {
    when (destination) {
        Destinations.SignIn -> goToSignInFromSplashScreen(navController)
        Destinations.Home -> goToHomeScreen(navController)
        else -> goToSignInFromSplashScreen(navController)
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





