package com.nat.greco

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
import com.nat.greco.base.navigation.NavApp
import com.nat.greco.base.permissions.PermissionViewModel
import com.nat.greco.ui.theme.CouriersAppTheme

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
            CouriersAppTheme(
                darkTheme = false
            ) {

                Surface(modifier = Modifier.fillMaxSize()) {
                    NavApp()
                }
            }
        }
    }

}
