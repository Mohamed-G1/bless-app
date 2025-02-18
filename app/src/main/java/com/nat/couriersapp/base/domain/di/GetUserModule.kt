package com.nat.couriersapp.base.domain.di

import com.nat.couriersapp.base.domain.userManager.GetUserDataManager
import com.nat.couriersapp.base.domain.userManager.GetUserDataManagerImpl
import org.koin.dsl.module

val getUserModule = module {
    single<GetUserDataManager> {
        GetUserDataManagerImpl(get())
    }


}