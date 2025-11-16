package com.nat.greco.base

import android.app.Application
import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import com.nat.greco.base.domain.di.localDataSourceModule
import com.nat.greco.base.network.di.networkModule
import com.nat.greco.screens.accounts.di.accountsModule
import com.nat.greco.screens.addNewClient.di.CustomerModule
import com.nat.greco.screens.addNewOrders.di.newProductsModule
import com.nat.greco.screens.dayDetails.di.DayDetailsModule
import com.nat.greco.screens.dealingProducts.di.dealingProductModule
import com.nat.greco.screens.routeDetails.di.courierDetailsModule
import com.nat.greco.screens.home.di.homeModule
import com.nat.greco.screens.login.di.userModule
import com.nat.greco.screens.orderDetails.di.OrderDetailsModule
import com.nat.greco.screens.orderHistory.orderHistoryModule
import com.nat.greco.screens.orders.di.OrdersModule
import com.nat.greco.screens.priceList.di.priceListModule
import com.nat.greco.screens.promotionsList.di.PromotionModule
import com.nat.greco.screens.receviceStock.di.ReceiveStockModule
import com.nat.greco.screens.returnsScreen.ReturnsModule
import com.nat.greco.screens.splash.di.splashModule
import com.nat.greco.screens.stocks.di.stocksModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class GrecoApp : Application() {

    override fun onCreate() {
        super.onCreate()
            FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("FCM Token", "Token: ${task.result}")
                }

        }

        startKoin {
            androidContext(this@GrecoApp)
            modules(
                networkModule,
                splashModule,
                userModule,
                localDataSourceModule,
                homeModule,
                courierDetailsModule,
                orderHistoryModule,
                accountsModule,
                newProductsModule,
                dealingProductModule,
                stocksModule,
                priceListModule,
                DayDetailsModule,
                OrdersModule,
                ReceiveStockModule,
                OrderDetailsModule,
                ReturnsModule,
                PromotionModule,
                CustomerModule
            )
        }
    }
}