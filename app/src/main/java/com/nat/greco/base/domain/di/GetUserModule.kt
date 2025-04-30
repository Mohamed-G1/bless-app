package com.nat.greco.base.domain.di

import com.nat.greco.base.domain.userManager.GetUserDataManager
import com.nat.greco.base.domain.userManager.GetUserDataManagerImpl
import com.nat.greco.base.local.LocalDataSource
import com.nat.greco.base.local.LocalDataSourceImpl
import org.koin.dsl.module

val localDataSourceModule = module {
    single<GetUserDataManager> {
        GetUserDataManagerImpl(get())
    }

    single<LocalDataSource> {
        LocalDataSourceImpl(get())
    }


}