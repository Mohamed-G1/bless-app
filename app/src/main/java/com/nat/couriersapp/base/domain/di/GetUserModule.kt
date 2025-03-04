package com.nat.couriersapp.base.domain.di

import com.nat.couriersapp.base.domain.userManager.GetUserDataManager
import com.nat.couriersapp.base.domain.userManager.GetUserDataManagerImpl
import com.nat.couriersapp.base.local.LocalDataSource
import com.nat.couriersapp.base.local.LocalDataSourceImpl
import org.koin.dsl.module

val localDataSourceModule = module {
    single<GetUserDataManager> {
        GetUserDataManagerImpl(get())
    }

    single<LocalDataSource> {
        LocalDataSourceImpl(get())
    }


}