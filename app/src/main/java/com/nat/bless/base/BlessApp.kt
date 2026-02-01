package com.nat.bless.base

import android.app.Application
import com.nat.bless.base.domain.di.localDataSourceModule
import com.nat.bless.base.network.di.networkModule
import com.nat.bless.screens.accounts.di.accountsModule
import com.nat.bless.screens.addNewClient.di.CustomerModule
import com.nat.bless.screens.addNewOrders.di.newProductsModule
import com.nat.bless.screens.collect.di.CollectModule
import com.nat.bless.screens.dailyReport.DailyReportModule
import com.nat.bless.screens.dayDetails.di.DayDetailsModule
import com.nat.bless.screens.dealingProducts.di.dealingProductModule
import com.nat.bless.screens.routeDetails.di.courierDetailsModule
import com.nat.bless.screens.home.di.homeModule
import com.nat.bless.screens.login.di.userModule
import com.nat.bless.screens.orderDetails.di.OrderDetailsModule
import com.nat.bless.screens.orderHistory.orderHistoryModule
import com.nat.bless.screens.orders.di.OrdersModule
import com.nat.bless.screens.priceList.di.priceListModule
import com.nat.bless.screens.promotionsList.di.PromotionModule
import com.nat.bless.screens.receviceStock.di.ReceiveStockModule
import com.nat.bless.screens.returnsScreen.ReturnsModule
import com.nat.bless.screens.splash.di.splashModule
import com.nat.bless.screens.stocks.di.stocksModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class BlessApp : Application() {

    override fun onCreate() {
        super.onCreate()
//            FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    Log.d("FCM Token", "Token: ${task.result}")
//                }
//
//        }

        startKoin {
            androidContext(this@BlessApp)
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
                CustomerModule,
                CollectModule,
                DailyReportModule
            )
        }
    }
}