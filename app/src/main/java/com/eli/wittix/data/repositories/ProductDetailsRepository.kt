/*
 * Copyright (c) 2026 Eli Levi. All rights reserved.
 * Developed by Eli Levi - https://www.linkedin.com/in/elilevi/
 */

package com.eli.wittix.data.repositories

import com.eli.wittix.data.local.dao.ProductDao
import com.eli.wittix.data.remote.api.ProductsApi
import com.eli.wittix.domain.IProductDetailsRepository
import com.eli.wittix.domain.models.ProductDetails
import com.eli.wittix.mappers.toDetails
import com.eli.wittix.mappers.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProductDetailsRepository(
    private val api: ProductsApi,
    private val productDao: ProductDao,
) : IProductDetailsRepository {
    override fun observeProduct(id: Long): Flow<ProductDetails?> =
        productDao.observeProduct(id).map { it?.toDetails() }

    override suspend fun refresh(id: Long): Result<Unit> = safeCall {
        productDao.upsertAll(listOf(api.getProduct(id).toEntity()))
    }
}
