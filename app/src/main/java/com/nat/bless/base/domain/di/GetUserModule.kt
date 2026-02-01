package com.nat.bless.base.domain.di

import com.nat.bless.base.domain.userManager.GetUserDataManager
import com.nat.bless.base.domain.userManager.GetUserDataManagerImpl
import com.nat.bless.base.local.LocalDataSource
import com.nat.bless.base.local.LocalDataSourceImpl
import org.koin.dsl.module

val localDataSourceModule = module {
    single<GetUserDataManager> {
        GetUserDataManagerImpl(get())
    }

    single<LocalDataSource> {
        LocalDataSourceImpl(get())
    }


}