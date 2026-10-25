package com.eli.wittix

import android.app.Application
import com.eli.wittix.di.databaseModule
import com.eli.wittix.di.networkModule
import com.eli.wittix.di.repositoryModule
import com.eli.wittix.di.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(networkModule, databaseModule, repositoryModule, viewModelsModule)
        }
    }
}
