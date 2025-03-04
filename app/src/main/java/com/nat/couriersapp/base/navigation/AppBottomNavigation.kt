package com.nat.couriersapp.base.navigation

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.nat.couriersapp.ui.theme.CompactTypography
import com.nat.couriersapp.ui.theme.WhiteGray

@SuppressLint("ResourceType")
@Composable
fun AppBottomNavigation(navController: NavController) {
    val bottomScreens = remember {
        listOf(
            BottomScreens.HomeScreen,
            BottomScreens.ScanScreen,
            BottomScreens.ControllerScreen,
            BottomScreens.MoreScreen
        )
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val currentRoute = navBackStackEntry?.destination?.route
        ?: BottomScreens.HomeScreen::class.qualifiedName.orEmpty()
    var navigationSelectedItem by rememberSaveable {
        mutableIntStateOf(0)
    }


    // Check if the current screen should have a bottom bar
    val shouldShowBottomBar = bottomScreens.any { it.route::class.qualifiedName == currentRoute }

    // Delay the bottom bar hiding until navigation is fully completed
    var showBottomBar by rememberSaveable { mutableStateOf(false) }
    var previousRoute by rememberSaveable { mutableStateOf<String?>(null) }

    LaunchedEffect(currentRoute) {
        // Update the previous route and handle bottom bar visibility
        if (!shouldShowBottomBar) {
            // Wait until the navigation is settled
            // Adjust this delay to match your screen transition time
            showBottomBar = false
        } else {
            showBottomBar = true
        }
        previousRoute = currentRoute
    }


    // Check if the current screen is one of the bottomScreens
    AnimatedVisibility(
        visible = showBottomBar,
        enter = fadeIn(animationSpec = tween(durationMillis = 500)) +
                slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(durationMillis = 500)
                ),
        exit = fadeOut(animationSpec = tween(durationMillis = 500)) +
                slideOutVertically(
                    targetOffsetY = { it },
                    animationSpec = tween(durationMillis = 500)
                )
    ) {
        NavigationBar(
            containerColor = Color.White,
            modifier = Modifier.border(width = 1.dp, color = WhiteGray)
        ) {
            bottomScreens.forEachIndexed { index, screen ->
                //To compare with the bottomBar route
                val isSelected by remember(currentRoute) {
                    derivedStateOf { currentRoute == screen.route::class.qualifiedName }
                }

                NavigationBarItem(
                    selected = index == navigationSelectedItem,
                    onClick = {
                        navigationSelectedItem = index

                        navController.navigate(screen.route) {
                            popUpTo(Destinations.Home) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = Color.Transparent,
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = Color.Unspecified,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        unselectedTextColor = Color.Unspecified

                    ),
                    icon = {
                        val color =
                            if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                painter = painterResource(id = screen.icon),
                                contentDescription = null,
                                modifier = Modifier.size(20.dp),

                                )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = stringResource(id = screen.name),
                                style = CompactTypography.labelMedium.copy(

                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            if (isSelected) {
                                Spacer(
                                    modifier = Modifier
                                        .size(4.dp)
                                        .clip(CircleShape)
                                        .background(MaterialTheme.colorScheme.secondary)
                                )
                            }
                        }
                    },
                )
            }
        }
    }
}