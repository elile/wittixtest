package com.eli.wittix

import android.app.Application
import android.content.pm.ApplicationInfo
import android.os.StrictMode
import com.eli.wittix.di.databaseModule
import com.eli.wittix.di.networkModule
import com.eli.wittix.di.repositoryModule
import com.eli.wittix.di.viewModelsModule
import leakcanary.LeakCanary
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

// Dev tools, debug builds only. Flip to turn on/off.
private const val STRICT_MODE_ENABLED = false
private const val LEAK_CANARY_ENABLED = false

class App : Application() {
    override fun onCreate() {
        val debuggable = applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
        if (debuggable && STRICT_MODE_ENABLED) enableStrictMode()
        super.onCreate()
        // LeakCanary is debug-only (compileOnly here); it auto-installs, the flag stops heap dumps + icon.
        if (debuggable) {
            LeakCanary.config = LeakCanary.config.copy(dumpHeap = LEAK_CANARY_ENABLED)
            LeakCanary.showLeakDisplayActivityLauncherIcon(LEAK_CANARY_ENABLED)
        }
        startKoin {
            androidContext(this@App)
            modules(networkModule, databaseModule, repositoryModule, viewModelsModule)
        }
    }

    // Logs violations to Logcat (tag "StrictMode") instead of crashing.
    private fun enableStrictMode() {
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build())
        StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build())
    }
}
