package com.nat.couriersapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.nat.couriersapp.base.navigation.NavApp
import com.nat.couriersapp.base.permissions.PermissionViewModel
import com.nat.couriersapp.ui.theme.CouriersAppTheme

class MainActivity : ComponentActivity() {

//    private val locationReceiver = LocationReceiver()

    private val viewModel: PermissionViewModel by viewModels()

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // disable LTR because the app is only supports arabic language
        window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL
        enableEdgeToEdge()
        setContent {
            CouriersAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    NavApp()
                }
            }
        }
    }

}
