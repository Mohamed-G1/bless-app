package com.nat.couriersapp.base

import android.app.Application
import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import com.google.firebase.messaging.FirebaseMessaging
import com.nat.couriersapp.base.domain.di.localDataSourceModule
import com.nat.couriersapp.base.network.di.networkModule
import com.nat.couriersapp.screens.courierDetails.di.courierDetailsModule
import com.nat.couriersapp.screens.home.di.homeModule
import com.nat.couriersapp.screens.login.di.userModule
import com.nat.couriersapp.screens.splash.di.splashModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class CouriersApp : Application() {

    override fun onCreate() {
        super.onCreate()
            FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("FCM Token", "Token: ${task.result}")
                }

        }

        startKoin {
            androidContext(this@CouriersApp)
            modules(
                networkModule,
                splashModule,
                userModule,
                localDataSourceModule,
                homeModule,
                courierDetailsModule
            )
        }
    }
}