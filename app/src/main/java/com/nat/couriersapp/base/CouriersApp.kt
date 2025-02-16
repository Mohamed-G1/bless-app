package com.nat.couriersapp.base

import android.app.Application
import com.nat.couriersapp.base.network.di.networkModule
import com.nat.couriersapp.screens.splash.di.splashModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class CouriersApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CouriersApp)
            modules(
                networkModule,
                splashModule,
            )
        }
    }
}