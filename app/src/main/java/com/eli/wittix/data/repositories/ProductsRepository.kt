/*
 * Copyright (c) 2026 Eli Levi. All rights reserved.
 * Developed by Eli Levi - https://www.linkedin.com/in/elilevi/
 */

package com.eli.wittix.data.repositories

import android.util.Log
import com.eli.wittix.data.local.dao.CategoryDao
import com.eli.wittix.data.local.dao.ProductDao
import com.eli.wittix.data.remote.api.ProductsApi
import com.eli.wittix.domain.IProductsRepository
import com.eli.wittix.domain.models.Category
import com.eli.wittix.domain.models.Product
import com.eli.wittix.mappers.toDomain
import com.eli.wittix.mappers.toEntity
import com.eli.wittix.mappers.toProduct
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

const val PAGE_SIZE = 20
private const val TAG = "ProductsRepository"

class ProductsRepository(
    private val api: ProductsApi,
    private val productDao: ProductDao,
    private val categoryDao: CategoryDao,
) : IProductsRepository {
    override fun observeProducts(query: String, category: String?): Flow<List<Product>> =
        productDao.observeProducts(query, category).map { list -> list.map { it.toProduct() } }

    override fun observeCategories(): Flow<List<Category>> =
        categoryDao.observeCategories().map { list -> list.map { it.toDomain() } }

    // API can't combine search + category; category wins (smaller set), local query applies both.
    override suspend fun loadPage(query: String, category: String?, page: Int): Result<Boolean> =
        safeCall {
            val skip = page * PAGE_SIZE
            Log.i(TAG, "loadPage page=$page skip=$skip query='$query' category=$category")
            val res = when {
                category != null -> api.getProductsByCategory(category, PAGE_SIZE, skip)
                query.isNotBlank() -> api.searchProducts(query, PAGE_SIZE, skip)
                else -> api.getProducts(PAGE_SIZE, skip)
            }
            productDao.upsertAll(res.products.map { it.toEntity() })
            (res.skip + res.products.size < res.total).also { hasMore ->
                Log.d(TAG, "loadPage page=$page got=${res.products.size} total=${res.total} hasMore=$hasMore")
            }
        }.onFailure { Log.w(TAG, "loadPage page=$page failed", it) }

    override suspend fun refreshCategories(): Result<Unit> = safeCall {
        categoryDao.upsertAll(api.getCategories().map { it.toEntity() })
    }
}
