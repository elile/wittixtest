package com.eli.wittix.di

import com.eli.wittix.screens.cart.CartViewModel
import com.eli.wittix.screens.productdetail.ProductDetailViewModel
import com.eli.wittix.screens.productlist.ProductListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelsModule = module {
    viewModelOf(::ProductListViewModel)
    viewModelOf(::ProductDetailViewModel)
    viewModelOf(::CartViewModel)
}
