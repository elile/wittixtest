package com.eli.wittix.repositories

import com.eli.wittix.data.local.dao.CartDao
import com.eli.wittix.domain.ICartRepository
import com.eli.wittix.domain.models.Cart
import kotlinx.coroutines.flow.Flow

class CartRepository(private val cartDao: CartDao) : ICartRepository {
    override fun observeCart(): Flow<Cart> = TODO()
    override fun observeCount(): Flow<Int> = TODO()
    override suspend fun add(productId: Long): Unit = TODO()
    override suspend fun setQuantity(productId: Long, quantity: Int): Unit = TODO()
    override suspend fun remove(productId: Long): Unit = TODO()
    override suspend fun clear(): Unit = TODO()
}
