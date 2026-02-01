package com.nat.bless.base.navigation

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
import com.nat.bless.screens.addNewClient.presentation.AddNewCustomerScreen
import com.nat.bless.screens.ContractsScreen
import com.nat.bless.screens.dayDetails.presentation.DayDetailsScreen
import com.nat.bless.screens.addNewOrders.presentation.chooseCustomer.ChooseCustomerScreen
import com.nat.bless.screens.promotionsList.presentation.PromotionScreen
import com.nat.bless.screens.orderDetails.presentation.OrderDetailsScreen
import com.nat.bless.screens.addNewOrders.presentation.AddNewProductsScreen
import com.nat.bless.screens.confirmOrder.ConfirmOrderScreen
import com.nat.bless.screens.receviceStock.presentation.ReceiveStockScreen
import com.nat.bless.screens.ReturnsDetailsScreen
import com.nat.bless.screens.returnsScreen.ReturnsScreen
import com.nat.bless.screens.accounts.AccountsScreen
import com.nat.bless.screens.accounts.presentation.AccountsViewModel
import com.nat.bless.screens.addNewClient.presentation.AddNewCustomerViewModel
import com.nat.bless.screens.addNewClient.presentation.MapScreen
import com.nat.bless.screens.addNewOrders.presentation.NewProductsViewModel
import com.nat.bless.screens.addNewOrders.presentation.chooseCustomer.ChooseCustomerViewModel
import com.nat.bless.screens.orders.presentation.OrdersScreen
import com.nat.bless.screens.routeDetails.presentation.RouteDetailsScreen
import com.nat.bless.screens.routeDetails.presentation.RouteDetailsViewModel
import com.nat.bless.screens.home.presentation.HomeScreen
import com.nat.bless.screens.home.presentation.HomeViewModel
import com.nat.bless.screens.orderHistory.OrderHistoryScreen
import com.nat.bless.screens.login.presentation.LoginScreen
import com.nat.bless.screens.login.presentation.LoginViewModel
import com.nat.bless.screens.stocks.peresentation.StockScreen
import com.nat.bless.screens.notifications.NotificationScreen
import com.nat.bless.screens.dealingProducts.peresentation.DealingProductsScreen
import com.nat.bless.screens.clients.CustomersScreen
import com.nat.bless.screens.clients.CustomersViewModel
import com.nat.bless.screens.collect.presenation.CollectScreen
import com.nat.bless.screens.collect.presenation.CollectViewModel
import com.nat.bless.screens.confirmOrder.ConfirmOrderViewModel
import com.nat.bless.screens.dailyReport.DailyReportScreen
import com.nat.bless.screens.dailyReport.DailyReportViewModel
import com.nat.bless.screens.dayDetails.presentation.DayDetailsViewModel
import com.nat.bless.screens.dealingProducts.peresentation.DealingProductsViewModel
import com.nat.bless.screens.editableConfirmOrder.EditableConfirmOrderScreen
import com.nat.bless.screens.editableConfirmOrder.EditableConfirmOrderViewModel
import com.nat.bless.screens.home.domain.models.Route
import com.nat.bless.screens.orderDetails.presentation.OrderDetailsViewModel
import com.nat.bless.screens.orderHistory.OrderHistoryDetailsScreen
import com.nat.bless.screens.orderHistory.OrderHistoryDetailsViewModel
import com.nat.bless.screens.orderHistory.OrderHistoryViewModel
import com.nat.bless.screens.orders.presentation.OrdersViewModel
import com.nat.bless.screens.priceList.presentation.PriceListScreen
import com.nat.bless.screens.priceList.presentation.PriceListViewModel
import com.nat.bless.screens.promotionsList.presentation.PromotionViewModel
import com.nat.bless.screens.receviceStock.presentation.ReceiveStockViewModel
import com.nat.bless.screens.returnsScreen.ReturnsViewModel
import com.nat.bless.screens.splash.presentation.SplashScreen
import com.nat.bless.screens.splash.presentation.SplashViewModel
import com.nat.bless.screens.stocks.peresentation.StockViewModel
import com.nat.bless.utils.canNavigate
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
                    openNewOrderScreen = {
                        if (navController.canNavigate)
                            navController.navigate(Destinations.NewOrderScreen)
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
                    onDailyReportClicked = { date ->
                        if (navController.canNavigate)
                            navController.navigate(Destinations.DailyReport(date))
                    }

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
                    onLastOrdersClicked = { id ->
                        if (navController.canNavigate)
                            navController.navigate(Destinations.OrderHistory(id))
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
                    onPromotionsClicked = { id ->
                        if (navController.canNavigate)
                            navController.navigate(Destinations.Promotions(id))

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
                    },
                    openCollectScreen = { id ->
                        if (navController.canNavigate)
                            navController.navigate(
                                Destinations.Collect(
                                    id,
                                    comingFromConfirmOrder = false
                                )
                            )
                    },
                    onCartClicked = { id ->
                        if (navController.canNavigate)
                            navController.navigate(Destinations.EditableConfirmOrder(id))
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

            composable<Destinations.DailyReport> {
                val args = it.toRoute<Destinations.DailyReport>()
                val viewModel: DailyReportViewModel = koinViewModel()
                val state by viewModel.state.collectAsStateWithLifecycle()
                DailyReportScreen(
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
                val viewModel: ReceiveStockViewModel = koinViewModel()
                val state by viewModel.state.collectAsStateWithLifecycle()
                ReceiveStockScreen(
                    state = state,
                    events = viewModel::sendEvent,
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
//                    navigateToConfirmOrder = { orderid, customerid ->
//                        if (navController.canNavigate)
//                            navController.navigate(Destinations.ConfirmOrder(orderid, customerid))
//                    },

                    navigateToEditableConfirmOrder = { id ->
                        if (navController.canNavigate)
                            navController.navigate(Destinations.EditableConfirmOrder(id))
                    }
                )
            }
            composable<Destinations.ConfirmOrder> {
                val args = it.toRoute<Destinations.ConfirmOrder>()
                val viewModel: ConfirmOrderViewModel = koinViewModel()
                val state by viewModel.state.collectAsStateWithLifecycle()
                ConfirmOrderScreen(
                    id = args.id,
                    customerId = args.customerid,
                    state = state,
                    events = viewModel::sendEvent,
                    onBackClicked = {
                        if (navController.canNavigate)
                            navController.navigateUp()
                    },
                    popStack = {
                        if (navController.canNavigate)
                            repeat(2) {
                                navController.popBackStack()
                            }
                    },
                    navigateToCollectScreen = { customerId ->
                        if (navController.canNavigate)
                            navController.navigate(
                                Destinations.Collect(
                                    customerId,
                                    comingFromConfirmOrder = true
                                )
                            )
                    }
                )
            }


            composable<Destinations.EditableConfirmOrder> {
                val args = it.toRoute<Destinations.EditableConfirmOrder>()
                val viewModel: EditableConfirmOrderViewModel = koinViewModel()
                val state by viewModel.state.collectAsStateWithLifecycle()
                EditableConfirmOrderScreen(
                    customerId = args.customerid,
                    state = state,
                    events = viewModel::sendEvent,
                    onBackClicked = {
                        if (navController.canNavigate)
                            navController.navigateUp()
                    },
                    navigateToConfirmOrderScreen = { orderid, customerid ->
                        if (navController.canNavigate)
                            navController.navigate(Destinations.ConfirmOrder(orderid, customerid))
                    }


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
                val args = it.toRoute<Destinations.OrderHistory>()
                val viewModel: OrderHistoryViewModel = koinViewModel()
                val state by viewModel.state.collectAsStateWithLifecycle()
                OrderHistoryScreen(
                    customerId = args.id,
                    state = state,
                    events = viewModel::sendEvent,
                    onOrderClicked = { id ->
                        if (navController.canNavigate) {
                            navController.navigate(
                                Destinations.OrderHistoryDetails(
                                    id
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

            composable<Destinations.OrderHistoryDetails> { backStackEntry ->
                val args = backStackEntry.toRoute<Destinations.OrderHistoryDetails>()
                val viewModel: OrderHistoryDetailsViewModel = koinViewModel()
                val state by viewModel.state.collectAsStateWithLifecycle()
                OrderHistoryDetailsScreen(
                    orderId = args.id,
                    state = state,
                    events = viewModel::sendEvent,
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
                val viewModel: CustomersViewModel = koinViewModel()
                val state by viewModel.state.collectAsStateWithLifecycle()
                CustomersScreen(state = state, events = viewModel::sendEvent)
            }

            composable<Destinations.Requests> {
                val viewModel: OrdersViewModel = koinViewModel()
                val state by viewModel.state.collectAsStateWithLifecycle()
                OrdersScreen(
                    state = state,
                    events = viewModel::sendEvent,
                    onOrderClicked = { model ->
                        if (navController.canNavigate)
                            navController.navigate(Destinations.OrderDetailsScreen(model))
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

            composable<Destinations.Promotions> {
                val args = it.toRoute<Destinations.Promotions>()
                val viewModel: PromotionViewModel = koinViewModel()
                val state by viewModel.state.collectAsStateWithLifecycle()
                PromotionScreen(
                    customerId = args.customerId,
                    state = state,
                    events = viewModel::sendEvent,
                    onBackClicked = {
                        if (navController.canNavigate)
                            navController.navigateUp()
                    }
                )
            }
            composable<Destinations.NewOrderScreen> {
                val viewModel: ChooseCustomerViewModel = koinViewModel()
                val state by viewModel.state.collectAsStateWithLifecycle()
                ChooseCustomerScreen(
                    state = state,
                    onBackClicked = {
                        if (navController.canNavigate)
                            navController.navigateUp()
                    },
                    navigateToProducts = { id ->
                        if (navController.canNavigate)
                            navController.navigate(Destinations.Products(id))
                    }
                )
            }
            composable<Destinations.AddNewClientScreen> {
                val viewModel: AddNewCustomerViewModel = koinViewModel()
                val state by viewModel.state.collectAsStateWithLifecycle()
                AddNewCustomerScreen(
                    state = state,
                    events = viewModel::sendEvent,
                    onBackClicked = {
                        if (navController.canNavigate)
                            navController.navigateUp()
                    },
                    openMapScreen = {
                        if (navController.canNavigate)
                            navController.navigate(Destinations.Map)
                    },

                    )
            }

            composable<Destinations.Map> {
                MapScreen(
                    onLocationPicked = { name, lat, lng ->
                        if (navController.canNavigate) {
                        }
//                            navController.navigate(
//                                Destinations.AddNewClientScreen(
//                                    name, lat, lng
//                                )
//                            )

                    },
                    onClose = {
                        if (navController.canNavigate)
                            navController.navigateUp()
                    }
                )
            }

            composable<Destinations.OrderDetailsScreen> {
                val args = it.toRoute<Destinations.OrderDetailsScreen>()
                val viewModel: OrderDetailsViewModel = koinViewModel()
                val state by viewModel.state.collectAsStateWithLifecycle()
                OrderDetailsScreen(
                    orderId = args.orderId,
                    state = state,
                    events = viewModel::sendEvent,
                    onBackClicked = {
                        if (navController.canNavigate)
                            navController.navigateUp()
                    },
                    navigateToReturn = { id ->
                        if (navController.canNavigate)
                            navController.navigate(Destinations.Returns(id))
                    }
                )
            }

            composable<Destinations.Returns> {
                val args = it.toRoute<Destinations.Returns>()
                val viewModel: ReturnsViewModel = koinViewModel()
                val state by viewModel.state.collectAsStateWithLifecycle()
                ReturnsScreen(
                    orderId = args.orderId,
                    state = state,
                    events = viewModel::sendEvent,
                    onBackClicked = {
                        if (navController.canNavigate)
                            navController.navigateUp()
                    },
                    popStack = {
                        if (navController.canNavigate)
//                            navController.navigate(Destinations.ReturnsDetails)
                            repeat(2) {
                                navController.popBackStack()
                            }
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

            composable<Destinations.Collect> {
                val viewModel: CollectViewModel = koinViewModel()
                val state by viewModel.state.collectAsStateWithLifecycle()
                val args = it.toRoute<Destinations.Collect>()
                CollectScreen(
                    state = state,
                    events = viewModel::sendEvent,
                    customerId = args.customerId,
                    onBackClicked = {
                        if (navController.canNavigate)
                            navController.navigateUp()
                    },
                    popBack = {
                        if (navController.canNavigate)
                            navController.popBackStack()
//                            repeat(2) {
//                                navController.popBackStack()
//                            }
                    },
                    onSkipClicked = {
                        repeat(4) {
                            navController.popBackStack()
                        }
                    },
                    isComingFromConfirmOrder = args.comingFromConfirmOrder
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





