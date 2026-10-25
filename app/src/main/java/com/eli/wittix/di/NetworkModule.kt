package com.eli.wittix.di

import com.eli.wittix.data.remote.RemoteProvider
import org.koin.dsl.module

val networkModule = module {
    single { RemoteProvider.productsApi() }
}
