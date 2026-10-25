package com.eli.wittix.data.repositories

import com.eli.wittix.data.local.dao.CategoryDao
import com.eli.wittix.data.local.dao.ProductDao
import com.eli.wittix.data.local.entities.CategoryEntity
import com.eli.wittix.data.local.entities.ProductEntity
import com.eli.wittix.data.remote.api.ProductsApi
import com.eli.wittix.data.remote.dto.CategoryDto
import com.eli.wittix.data.remote.dto.ProductDto
import com.eli.wittix.data.remote.dto.ProductsResponseDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ProductsRepositoryTest {
    private val calls = mutableListOf<String>()
    private val saved = mutableListOf<ProductEntity>()
    private val total = 25
    private var offline = false

    private fun page(skip: Int) = if (offline) error("offline") else ProductsResponseDto(
        products = (skip until minOf(skip + PAGE_SIZE, total)).map {
            ProductDto(it.toLong(), "t", "d", 1.0, 0.0, 4.0, 1, null, "c", "th")
        },
        total = total, skip = skip, limit = PAGE_SIZE,
    )

    private val api = object : ProductsApi {
        override suspend fun getProducts(limit: Int, skip: Int) = page(skip).also { calls += "all@$skip" }
        override suspend fun searchProducts(query: String, limit: Int, skip: Int) = page(skip).also { calls += "search:$query@$skip" }
        override suspend fun getProductsByCategory(slug: String, limit: Int, skip: Int) = page(skip).also { calls += "cat:$slug@$skip" }
        override suspend fun getProduct(id: Long): ProductDto = error("unused")
        override suspend fun getCategories(): List<CategoryDto> = error("unused")
    }

    private val productDao = object : ProductDao {
        override fun observeProducts(query: String, category: String?): Flow<List<ProductEntity>> = emptyFlow()
        override fun observeProduct(id: Long): Flow<ProductEntity?> = emptyFlow()
        override suspend fun upsertAll(products: List<ProductEntity>) { saved += products }
    }

    private val categoryDao = object : CategoryDao {
        override fun observeCategories(): Flow<List<CategoryEntity>> = emptyFlow()
        override suspend fun upsertAll(categories: List<CategoryEntity>) = Unit
    }

    private val repo = ProductsRepository(api, productDao, categoryDao)

    @Test
    fun loadPage() = runBlocking {
        assertTrue(repo.loadPage("", null, 0).getOrThrow())   // 20 of 25
        assertFalse(repo.loadPage("", null, 1).getOrThrow())  // 25 of 25
        repo.loadPage("phone", "beauty", 0)
        repo.loadPage("phone", null, 1)
        assertEquals(listOf("all@0", "all@20", "cat:beauty@0", "search:phone@20"), calls)
        assertEquals(50, saved.size)

        offline = true
        assertTrue(repo.loadPage("", null, 0).isFailure)
    }
}
