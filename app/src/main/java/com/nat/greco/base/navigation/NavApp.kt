package com.nat.greco.base.navigation

import android.os.Build
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
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nat.greco.screens.AddNewClientScreen
import com.nat.greco.screens.ContractsScreen
import com.nat.greco.screens.dayDetails.presentation.DayDetailsScreen
import com.nat.greco.screens.addNewOrders.AddNewOrderScreen
import com.nat.greco.screens.OffersScreen
import com.nat.greco.screens.OrderDetailsScreen
import com.nat.greco.screens.addNewOrders.presentation.AddNewProductsScreen
import com.nat.greco.screens.ProductsDetails
import com.nat.greco.screens.ReceiveStockScreen
import com.nat.greco.screens.ReturnsDetailsScreen
import com.nat.greco.screens.ReturnsScreen
import com.nat.greco.screens.accounts.AccountsScreen
import com.nat.greco.screens.accounts.presentation.AccountsViewModel
import com.nat.greco.screens.addNewOrders.presentation.NewProductsViewModel
import com.nat.greco.screens.routeDetails.domain.models.OrderLine
import com.nat.greco.screens.orders.OrdersScreen
import com.nat.greco.screens.routeDetails.presentation.RouteDetailsScreen
import com.nat.greco.screens.routeDetails.presentation.RouteDetailsViewModel
import com.nat.greco.screens.home.presentation.HomeScreen
import com.nat.greco.screens.home.presentation.HomeViewModel
import com.nat.greco.screens.orderHistory.OrderHistoryScreen
import com.nat.greco.screens.login.presentation.LoginScreen
import com.nat.greco.screens.login.presentation.LoginViewModel
import com.nat.greco.screens.stocks.peresentation.StockScreen
import com.nat.greco.screens.notifications.NotificationScreen
import com.nat.greco.screens.dealingProducts.peresentation.DealingProductsScreen
import com.nat.greco.screens.clients.ClientsScreen
import com.nat.greco.screens.dayDetails.presentation.DayDetailsViewModel
import com.nat.greco.screens.dealingProducts.peresentation.DealingProductsViewModel
import com.nat.greco.screens.home.domain.models.Route
import com.nat.greco.screens.orderHistory.OrderHistoryDetailsScreen
import com.nat.greco.screens.orderHistory.OrderHistoryViewModel
import com.nat.greco.screens.priceList.presentation.PriceListScreen
import com.nat.greco.screens.priceList.presentation.PriceListViewModel
import com.nat.greco.screens.splash.presentation.SplashScreen
import com.nat.greco.screens.splash.presentation.SplashViewModel
import com.nat.greco.screens.stocks.peresentation.StockViewModel
import com.nat.greco.utils.canNavigate
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
                    onClick = { model, note ->
                        if (navController.canNavigate)
                            navController.navigate(Destinations.RouteDetails(model, note))
                    },
                    navigateToNotification = {
                        if (navController.canNavigate)
                            navController.navigate(Destinations.Notification)
                    },
                    openNewRequestScreen = {
                        if (navController.canNavigate)
                            navController.navigate(Destinations.Products)
                    },
                    openAddClientScreen = {
                        if (navController.canNavigate)
                            navController.navigate(Destinations.AddNewClientScreen)
                    },
                    signOut = {
                        if (navController.canNavigate)
                            signOutFromHome(navController)
                    },
                    onDayDetailsClicked = { date ->
                        if (navController.canNavigate)
                            navController.navigate(Destinations.DayDetails(date))
                    },

                    )
            }

            composable<Destinations.RouteDetails>(
                typeMap = mapOf(
                    typeOf<Route>() to CustomNavType.HomeModel
                )
            ) {
                val args = it.toRoute<Destinations.RouteDetails>()
                val viewModel: RouteDetailsViewModel = koinViewModel()
                val state by viewModel.state.collectAsStateWithLifecycle()
                RouteDetailsScreen(
                    state = state,
                    events = viewModel::sendEvent,
                    route = args.roues,
                    note = args.note,
                    onBackClicked = {
                        if (navController.canNavigate)
                            navController.navigateUp()
                    },
                    onLastOrdersClicked = {
                        if (navController.canNavigate)
                            navController.navigate(Destinations.OrderHistory)
                    },
                    onDealingProductsClicked = { id ->
                        if (navController.canNavigate)
                            navController.navigate(Destinations.DealingProducts(id))
                    },
                    onAccountsClicked = { customerid ->
                        if (navController.canNavigate)
                            navController.navigate(
                                Destinations.Accounts(
                                    customerid = customerid
                                )
                            )
                    },
                    onOffersClicked = {
                        if (navController.canNavigate)
                            navController.navigate(Destinations.Offers)

                    },
                    openNewProductsScreen = { id ->
                        if (navController.canNavigate)
                            navController.navigate(Destinations.Products(id))
                    },
                    openContractsScreen = { contract ->
                        if (navController.canNavigate)
                            navController.navigate(Destinations.Contracts(contract))
                    },
                    openPriceListScreen = { id ->
                        if (navController.canNavigate)
                            navController.navigate(Destinations.PriceList(id))
                    }
                )
            }

            composable<Destinations.PriceList> {
                val args = it.toRoute<Destinations.PriceList>()
                val viewModel: PriceListViewModel = koinViewModel()
                val state by viewModel.state.collectAsStateWithLifecycle()
                PriceListScreen(
                    customerId = args.customerid,
                    state = state,
                    events = viewModel::sendEvent,
                    onBackClicked = {
                        if (navController.canNavigate)
                            navController.navigateUp()
                    }
                )
            }

            composable<Destinations.Contracts> {
                val args = it.toRoute<Destinations.Contracts>()
                ContractsScreen(
                    contract = args.contract,
                    onBackClicked = {
                        if (navController.canNavigate)
                            navController.navigateUp()
                    })
            }

            composable<Destinations.Notification> {
                NotificationScreen(
                    onBackClicked = {
                        if (navController.canNavigate)
                            navController.navigateUp()
                    }
                )
            }
            composable<Destinations.DayDetails> {
                val args = it.toRoute<Destinations.DayDetails>()
                val viewModel: DayDetailsViewModel = koinViewModel()
                val state by viewModel.state.collectAsStateWithLifecycle()
                DayDetailsScreen(
                    date = args.date,
                    state = state,
                    events = viewModel::sendEvent,
                    onBackClicked = {
                        if (navController.canNavigate)
                            navController.navigateUp()
                    }
                )
            }
            composable<Destinations.ReceiveStock> {
                ReceiveStockScreen(
                    onBackClicked = {
                        if (navController.canNavigate)
                            navController.navigateUp()
                    },
                    onConfirmClicked = {

                    }
                )
            }

            composable<Destinations.Products> {
                val viewModel: NewProductsViewModel = koinViewModel()
                val state by viewModel.state.collectAsStateWithLifecycle()
                val args = it.toRoute<Destinations.Products>()
                AddNewProductsScreen(
                    customerId = args.customerid,
                    state = state,
                    events = viewModel::sendEvent,
                    onBackClicked = {
                        if (navController.canNavigate)
                            navController.navigateUp()
                    },
                    navigateToProductDetails = {
                        if (navController.canNavigate)
                            navController.navigate(Destinations.ProductDetails)
                    }
                )
            }
            composable<Destinations.ProductDetails> {
                ProductsDetails(
                    onBackClicked = {
                        if (navController.canNavigate)
                            navController.navigateUp()
                    },

                    )
            }

            composable<Destinations.Accounts> {
                val viewModel: AccountsViewModel = koinViewModel()
                val state by viewModel.state.collectAsStateWithLifecycle()
                val args = it.toRoute<Destinations.Accounts>()
                AccountsScreen(
                    state = state,
                    events = viewModel::sendEvent,
                    customer_id = args.customerid,
                    onBackClicked = {
                        if (navController.canNavigate)
                            navController.navigateUp()
                    }
                )
            }

            composable<Destinations.OrderHistory> {
                val gson = Gson()

                val viewModel: OrderHistoryViewModel = koinViewModel()
                val state by viewModel.state.collectAsStateWithLifecycle()
                OrderHistoryScreen(
                    state = state,
                    events = viewModel::sendEvent,
                    onOrderClicked = { model, date, number, amount_untaxed,
                                       amount_tax,
                                       amount_total ->
                        if (navController.canNavigate) {
                            val jsonString = gson.toJson(model)
                            navController.navigate(
                                Destinations.OrderHistoryDetails(
                                    jsonString, date, number, amount_untaxed,
                                    amount_tax,
                                    amount_total
                                )
                            )
                        }

//                            navController.navigate(
//                                Destinations.OrderHistoryDetails(model),
//                            )
                    },
                    onBackClicked = {
                        if (navController.canNavigate)
                            navController.navigateUp()
                    }
                )
            }

            composable<Destinations.OrderHistoryDetails>
            { backStackEntry ->

                // 1. Get the route object which now contains the encoded string
                val args = backStackEntry.toRoute<Destinations.OrderHistoryDetails>()
                val jsonString = args.encodedOrderLines
                val gson = Gson()
                // 3. URL-decode the string argument
                val orderLines: List<OrderLine> = gson.fromJson(
                    jsonString,
                    object : TypeToken<List<OrderLine>>() {}.type
                )                // 4. Deserialize the JSON string back into your List<OrderLine> object
                OrderHistoryDetailsScreen(
                    model = orderLines,
                    orderDate = args.orderDate,
                    orderNumber = args.orderNumber,
                    amount_untaxed = args.amount_untaxed,
                    amount_tax = args.amount_tax,
                    amount_total = args.amount_total,
                    onBackClicked = {
                        if (navController.canNavigate)
                            navController.navigateUp()
                    }
                )
            }
            composable<Destinations.DealingProducts> {
                val args = it.toRoute<Destinations.DealingProducts>()
                val viewModel: DealingProductsViewModel = koinViewModel()
                val state by viewModel.state.collectAsStateWithLifecycle()
                DealingProductsScreen(
                    customerId = args.customerid,
                    state = state,
                    events = viewModel::sendEvent,
                    onBackClicked = {
                        if (navController.canNavigate)
                            navController.navigateUp()
                    },
                )
            }

            composable<Destinations.Clients> {
                ClientsScreen()
            }

            composable<Destinations.Requests> {
                OrdersScreen(
                    onOrderClicked = {
                        if (navController.canNavigate)
                            navController.navigate(Destinations.OrderDetailsScreen)
                    }
                )
            }

            composable<Destinations.Stock> {
                val viewModel: StockViewModel = koinViewModel()
                val state by viewModel.state.collectAsStateWithLifecycle()
                StockScreen(
                    state = state,
                    events = viewModel::sendEvent,
                    onReceiveClicked = {
                        if (navController.canNavigate)
                            navController.navigate(Destinations.ReceiveStock)
                    }
                )
            }

            composable<Destinations.Offers> {
                OffersScreen(
                    onBackClicked = {
                        if (navController.canNavigate)
                            navController.navigateUp()
                    }
                )
            }
            composable<Destinations.NewRequestScreen> {
                AddNewOrderScreen(
                    onBackClicked = {
                        if (navController.canNavigate)
                            navController.navigateUp()
                    },
                    navigateToProducts = {
                        if (navController.canNavigate)
                            navController.navigate(Destinations.Products)
                    }
                )
            }
            composable<Destinations.AddNewClientScreen> {
                AddNewClientScreen(
                    onBackClicked = {
                        if (navController.canNavigate)
                            navController.navigateUp()
                    }
                )
            }

            composable<Destinations.OrderDetailsScreen> {
                OrderDetailsScreen(
                    onBackClicked = {
                        if (navController.canNavigate)
                            navController.navigateUp()
                    },
                    navigateToReturn = {
                        if (navController.canNavigate)
                            navController.navigate(Destinations.Returns)
                    }
                )
            }

            composable<Destinations.Returns> {
                ReturnsScreen(
                    onBackClicked = {
                        if (navController.canNavigate)
                            navController.navigateUp()
                    },
                    navigateToReturnDetails = {
                        if (navController.canNavigate)
                            navController.navigate(Destinations.ReturnsDetails)
                    }
                )
            }

            composable<Destinations.ReturnsDetails> {
                ReturnsDetailsScreen(
                    onBackClicked = {
                        if (navController.canNavigate)
                            navController.navigateUp()
                    }
                )
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
        BottomScreens.ClientsScreen.route.toString() -> 2
        BottomScreens.RequestsScreen.route.toString() -> 3
        BottomScreens.StockScreen.route.toString() -> 4
        else -> 0
    }
}





