package com.eli.wittix.di

import com.eli.wittix.data.local.AppDatabase
import com.eli.wittix.data.local.LocalProvider
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single { LocalProvider.database(androidContext()) }
    single { get<AppDatabase>().productDao() }
    single { get<AppDatabase>().categoryDao() }
    single { get<AppDatabase>().cartDao() }
}
