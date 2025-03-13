package com.nat.couriersapp.base

import androidx.activity.SystemBarStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.toArgb

//@Composable
//private fun ChangeSystemBarsTheme(lightTheme: Boolean) {
//    val barColor = MaterialTheme.colorScheme.background.toArgb()
//    LaunchedEffect(lightTheme) {
//        if (lightTheme) {
//            enableEdgeToEdge(
//                statusBarStyle = SystemBarStyle.light(
//                    barColor, barColor,
//                ),
//                navigationBarStyle = SystemBarStyle.light(
//                    barColor, barColor,
//                ),
//            )
//        } else {
//            enableEdgeToEdge(
//                statusBarStyle = SystemBarStyle.dark(
//                    barColor,
//                ),
//                navigationBarStyle = SystemBarStyle.dark(
//                    barColor,
//                ),
//            )
//        }
//    }
//}