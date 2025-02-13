package com.siad.stayksa.base.navigation

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.siad.stayksa.screens.splash.presentation.SplashScreen
import com.siad.stayksa.screens.splash.presentation.SplashViewModel
import com.siad.stayksa.screens.socialMediaSignIn.GoogleSignInManager
import com.siad.stayksa.screens.socialMediaSignIn.UserSocialBody
import org.koin.androidx.compose.koinViewModel

/**
 * This file handle the app navigations
 * */
@Composable
fun NavApp(
    openPrivacyTermsPage: (() -> Unit)? = null
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    var userSocialBody by remember {
        mutableStateOf(UserSocialBody())
    }
    var isGoogleClicked by remember {
        mutableStateOf(false)
    }

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
            GoogleSignInManager.doGoogleSignIn(
                context = context,
                scope = scope,
                launcher = null,
                login = { user ->
                    userSocialBody = user
                    isGoogleClicked = true
                }
            )

        }

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

            composable<Destinations.OnBoarding> {
//                val viewModel: OnboardingViewModel = koinViewModel()
//                val state by viewModel.state.collectAsStateWithLifecycle()
//                val generalState by viewModel.generalState.collectAsStateWithLifecycle()
//
//                OnboardingScreen(
//                    state = state,
//                    generalState = generalState,
//                    onSkipClick = {
//                        viewModel.onEvent(it)
//                        goToSignInFromOnBoarding(navController)
//
//                    },
//                    onLastNextClick = {
//                        viewModel.onEvent(it)
//                        goToSignInFromOnBoarding(navController)
//                    },
//                    onExploreClick = {
//                        if (navController.canNavigate)
//                            navController.navigate(Destinations.Explore)
//                    },
//                    onLoginClick = {
//                        goToSignInFromOnBoardingScreen(navController)
//                    }
//                )
            }


//            composable<Destinations.SignIn> {
//                val viewModel: SignInViewModel = koinViewModel()
//                val state by viewModel.state.collectAsStateWithLifecycle()
//                val generalState by viewModel.generalState.collectAsStateWithLifecycle()
//
//                //Google
//                if (isGoogleClicked) {
//                    viewModel.checkOnSocialUser(
//                        request = SocialUserSignInRequest(
//                            driver = SocialMediaType.GOOGLE.value,
//                            email = userSocialBody.userEmail.orEmpty(),
//                            provider_id = userSocialBody.id.orEmpty(),
//                            provider_token = userSocialBody.token ?: "1e1f1er1fr1ff1r",
//
//                            )
//                    )
//
//                    isGoogleClicked = false
//                }
//
//                // FaceBook
//                val loginManager = LoginManager.getInstance()
//                val callbackManager = remember { CallbackManager.Factory.create() }
//
//                //This activity result to receive the facebook signed user via facebook
//                val facebookLauncher = rememberLauncherForActivityResult(
//                    loginManager.createLogInActivityResultContract(
//                        callbackManager,
//                        null
//                    )
//                ) {}
//                DisposableEffect(Unit) {
//                    loginManager.registerCallback(callbackManager,
//                        object : FacebookCallback<LoginResult> {
//                            override fun onCancel() {
//                                Log.d("##1", "Cancel")
//                            }
//
//                            override fun onError(error: FacebookException) {
//                                Log.d("##1", error.message.toString())
//                                SignInResult(
//                                    data = null,
//                                    errorMessage = error.message
//                                )
//                            }
//
//                            override fun onSuccess(result: LoginResult) {
//                                Log.d("##3", result.accessToken.token)
//                                // This to can get the user information, like his Email
//                                GraphRequest.newMeRequest(
//                                    result.accessToken
//                                ) { `object`, response ->
//                                    Log.v("##4", response.toString())
//                                    // Application code
//                                    try {
////                                        driver = ,
////                                        email = userSocialBody.userEmail.orEmpty(),
////                                        provider_token = userSocialBody.token ?: "1e1f1er1fr1ff1r"
//                                        val email = `object`?.getString("email")
//                                        val birthday = `object`?.getString("birthday")
//                                        val gender = `object`?.getString("gender")
//                                        val name = `object`?.getString("name")
//                                        val phone = `object`?.getString("phone")
//
//                                        userSocialBody = UserSocialBody(
//                                            id = result.accessToken.applicationId,
//                                            userName = name,
//                                            userEmail = email,
//                                            phoneNumber = phone,
//                                            token = result.accessToken.token ?: "1f08df8ff8",
//                                            driver = SocialMediaType.FACEBOOK.value,
//                                            provider_id = result.accessToken.applicationId,
//
//
//                                            )
////                                        viewModel.onFacebookSignInResult(loginResult)
//
//                                        viewModel.checkOnSocialUser(
//                                            request = SocialUserSignInRequest(
//                                                driver = SocialMediaType.FACEBOOK.value,
//                                                email = userSocialBody.userEmail,
//                                                provider_id = result.accessToken.applicationId,
//                                                provider_token = result.accessToken.token
//                                            )
//                                        )
//                                        viewModel.resetSocialSignInState()
//
//                                    } catch (ex: JSONException) {
//                                        Log.d("JSonException", ex.toString())
//                                    }
//                                }
//                            }
//
//                        })
//                    onDispose {
//                        loginManager.unregisterCallback(callbackManager)
//                    }
//                }
//
//                SignInScreen(
//                    state = state,
//                    generalState = generalState,
//                    userSocialBody = userSocialBody,
//                    event = viewModel::onEvent,
//                    navigateToHome = {
//                        goToHomeFromSignIn(navController)
//                    },
//                    onGoogleClicked = {
//                        viewModel.resetSocialSignInState()
//                        GoogleSignInManager.doGoogleSignIn(
//                            context = context,
//                            scope = scope,
//                            launcher = launcher,
//                            login = { user ->
//                                userSocialBody = user
//                                isGoogleClicked = true
//                            }
//                        )
//
//                    },
//                    onFacebookClicked = {
//                        facebookLauncher.launch(listOf("public_profile", "email"))
//                    },
//                    navigateToForgetPassword = {
//                        goToForgetPasswordFromSignIn(navController)
//                    },
//                    navigateToSignUp = { isNeedToCompleteRegister, userSocialBody ->
//                        goToSignUpFromSignIn(
//                            navController,
//                            isNeedToCompleteRegister,
//                            userSocialBody
//                        )
//                    }
//                )
//            }

//            composable<Destinations.SignUp>(
//                typeMap = mapOf(
//                    typeOf<UserSocialBody>() to CustomNavType.UserSocialInfo
//                )
//            ) {
//                val viewModel: SignUpViewModel = koinViewModel()
//                val state by viewModel.state.collectAsStateWithLifecycle()
//                val generalState by viewModel.generalState.collectAsStateWithLifecycle()
//
//                val args = it.toRoute<Destinations.SignUp>()
//
//                SignUpScreen(
//                    state = state,
//                    generalState = generalState,
//                    isNeedToCompleteRegister = args.isNeedToCompleteRegister,
//                    userSocialInfo = args.userSocialBody,
//                    event = viewModel::onEvent,
//                    navigateToSignIn = {
//                        goToSignInFromSignUp(navController)
//                    }, navigateToVerification = { userPhoneModel ->
//                        if (navController.canNavigate) {
//                            navController.navigate(
//                                Destinations.Verification(userPhoneModel)
//                            )
//                        }
//                    },
//                    openPrivacyTermsPage = openPrivacyTermsPage
//                )
//            }

//            composable<Destinations.Verification>(
//                typeMap = mapOf(
//                    typeOf<UserPhoneModel>() to CustomNavType.UserPhoneNumberModel
//                )
//            ) {
//                val viewModel: VerificationViewModel = koinViewModel()
//                val state by viewModel.state.collectAsStateWithLifecycle()
//                val generalState by viewModel.generalState.collectAsStateWithLifecycle()
//                //Receive the phone number from the sign up screen
//                val args = it.toRoute<Destinations.Verification>()
//                VerificationScreen(
//                    state = state,
//                    generalState = generalState,
//                    phoneNumber = args.userPhoneModel.phoneNumber.orEmpty(),
//                    countryCode = args.userPhoneModel.countryCode.orEmpty(),
//                    event = viewModel::onEvent,
//                    navigateToHome = {
//                        navController.navigate(Destinations.Explore)
//                    }
//                )
//            }

        }
    }


}

/** These functions to navigate to the destination screen and remove the previous screen from the back-stack
 * */



fun goToForgetPasswordFromSignIn(navController: NavHostController) {
    navController.navigate(Destinations.ForgetPassword) {
        popUpTo(Destinations.SignIn) {
            // keep the sign in screen in the back stack
            inclusive = false
        }
    }
}



fun goToSignInFromOnBoarding(navController: NavHostController) {
    navController.navigate(Destinations.SignIn) {
        popUpTo(Destinations.OnBoarding) {
            inclusive = true
        }
    }
}

fun goToSignInFromSignUp(navController: NavHostController) {
    navController.navigate(
        Destinations.SignIn
    ) {
        popUpTo(Destinations.SignUp) {
            inclusive = true
        }
    }
}


fun goToSignInFromOnBoardingScreen(navController: NavHostController) {
    navController.navigate(
        Destinations.SignIn
    ) {
        popUpTo(Destinations.OnBoarding) {
            inclusive = true
        }
    }
}

private fun goToOnBoardingFromSplashScreen(navController: NavController) {
    navController.navigate(
        Destinations.OnBoarding
    ) {
        popUpTo(Destinations.Splash) {
            inclusive = true
        }
    }
}

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
        Destinations.OnBoarding -> goToOnBoardingFromSplashScreen(navController)
        Destinations.SignIn -> goToSignInFromSplashScreen(navController)
        Destinations.Home -> goToHomeScreen(navController)
        else -> goToOnBoardingFromSplashScreen(navController)
    }
}


private fun getSelectedItemAccordingToBackstack(backstackState: NavDestination?): Int {
    return when (backstackState?.route) {
        BottomScreens.HomeScreen.route.toString() -> 0
        BottomScreens.BookingScreen.route.toString() -> 2
        BottomScreens.ExperienceScreen.route.toString() -> 3
        BottomScreens.ProfileScreen.route.toString() -> 4
        else -> 0
    }
}





