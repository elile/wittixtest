/*
 * Copyright (c) 2026 Eli Levi. All rights reserved.
 * Developed by Eli Levi - https://www.linkedin.com/in/elilevi/
 */

package com.eli.wittix.di

import com.eli.wittix.ui.screens.cart.CartViewModel
import com.eli.wittix.ui.screens.productdetail.ProductDetailViewModel
import com.eli.wittix.ui.screens.productlist.ProductListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelsModule = module {
    viewModelOf(::ProductListViewModel)
    viewModelOf(::ProductDetailViewModel)
    viewModelOf(::CartViewModel)
}
