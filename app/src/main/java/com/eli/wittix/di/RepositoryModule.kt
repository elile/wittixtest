package com.eli.wittix.di

import com.eli.wittix.domain.ICartRepository
import com.eli.wittix.domain.IProductDetailsRepository
import com.eli.wittix.domain.IProductsRepository
import com.eli.wittix.repositories.CartRepository
import com.eli.wittix.repositories.ProductDetailsRepository
import com.eli.wittix.repositories.ProductsRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::ProductsRepository) bind IProductsRepository::class
    singleOf(::ProductDetailsRepository) bind IProductDetailsRepository::class
    singleOf(::CartRepository) bind ICartRepository::class
}
