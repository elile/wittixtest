package com.eli.wittix.repositories

import com.eli.wittix.data.local.dao.ProductDao
import com.eli.wittix.data.remote.api.ProductsApi
import com.eli.wittix.domain.IProductDetailsRepository
import com.eli.wittix.domain.models.ProductDetails
import kotlinx.coroutines.flow.Flow

class ProductDetailsRepository(
    private val api: ProductsApi,
    private val productDao: ProductDao,
) : IProductDetailsRepository {
    override fun observeProduct(id: Long): Flow<ProductDetails?> = TODO()
    override suspend fun refresh(id: Long): Result<Unit> = TODO()
}
