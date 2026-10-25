package com.eli.wittix.domain

import com.eli.wittix.domain.models.ProductDetails
import kotlinx.coroutines.flow.Flow

interface IProductDetailsRepository {
    // Null until the product is in the local DB.
    fun observeProduct(id: Long): Flow<ProductDetails?>
    suspend fun refresh(id: Long): Result<Unit>
}
