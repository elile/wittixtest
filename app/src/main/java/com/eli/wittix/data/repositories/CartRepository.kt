package com.eli.wittix.data.repositories

import com.eli.wittix.data.local.dao.CartDao
import com.eli.wittix.domain.ICartRepository
import com.eli.wittix.domain.models.Cart
import com.eli.wittix.mappers.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CartRepository(private val cartDao: CartDao) : ICartRepository {
    override fun observeCart(): Flow<Cart> = cartDao.observeCart().map { list -> Cart(list.map { it.toDomain() }) }
    override fun observeCount(): Flow<Int> = cartDao.observeCount()
    override suspend fun add(productId: Long) = cartDao.add(productId)
    override suspend fun setQuantity(productId: Long, quantity: Int) = cartDao.setQuantity(productId, quantity)
    override suspend fun remove(productId: Long) = cartDao.remove(productId)
    override suspend fun clear() = cartDao.clear()
}
