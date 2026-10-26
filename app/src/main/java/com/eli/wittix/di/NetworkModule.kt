/*
 * Copyright (c) 2026 Eli Levi. All rights reserved.
 * Developed by Eli Levi - https://www.linkedin.com/in/elilevi/
 */

package com.eli.wittix.di

import com.eli.wittix.data.remote.RemoteProvider
import org.koin.dsl.module

val networkModule = module {
    single { RemoteProvider.productsApi() }
}
