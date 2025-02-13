package com.siad.stayksa.base

import android.app.Application
import com.siad.stayksa.base.network.di.networkModule
import com.siad.stayksa.features.splash.di.splashModule
import com.siad.stayksa.screens.onboarding.di.onboardingModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class StayKsaApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@StayKsaApp)
            modules(
                networkModule,
                splashModule,
                onboardingModule
            )
        }
    }
}