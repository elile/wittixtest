package com.eli.wittix.domain

import com.eli.wittix.domain.models.Category
import com.eli.wittix.domain.models.Product
import kotlinx.coroutines.flow.Flow

interface IProductsRepository {
    // Reads from the local DB; null category = all.
    fun observeProducts(query: String, category: String?): Flow<List<Product>>
    fun observeCategories(): Flow<List<Category>>

    // Fetches a page into the DB. Success(true) = more pages left.
    suspend fun loadPage(query: String, category: String?, page: Int): Result<Boolean>
    suspend fun refreshCategories(): Result<Unit>
}
