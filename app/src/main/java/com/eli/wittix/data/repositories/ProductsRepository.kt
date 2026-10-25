package com.eli.wittix.repositories

import com.eli.wittix.data.local.dao.CategoryDao
import com.eli.wittix.data.local.dao.ProductDao
import com.eli.wittix.data.remote.api.ProductsApi
import com.eli.wittix.domain.IProductsRepository
import com.eli.wittix.domain.models.Category
import com.eli.wittix.domain.models.Product
import kotlinx.coroutines.flow.Flow

class ProductsRepository(
    private val api: ProductsApi,
    private val productDao: ProductDao,
    private val categoryDao: CategoryDao,
) : IProductsRepository {
    override fun observeProducts(query: String, category: String?): Flow<List<Product>> = TODO()
    override fun observeCategories(): Flow<List<Category>> = TODO()
    override suspend fun loadPage(query: String, category: String?, page: Int): Result<Boolean> = TODO()
    override suspend fun refreshCategories(): Result<Unit> = TODO()
}
