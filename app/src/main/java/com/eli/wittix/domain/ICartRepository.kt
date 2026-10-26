/*
 * Copyright (c) 2026 Eli Levi. All rights reserved.
 * Developed by Eli Levi - https://www.linkedin.com/in/elilevi/
 */

package com.eli.wittix.domain

import com.eli.wittix.domain.models.Cart
import kotlinx.coroutines.flow.Flow

interface ICartRepository {
    fun observeCart(): Flow<Cart>
    fun observeCount(): Flow<Int>

    // Adds 1, or increments if already in cart.
    suspend fun add(productId: Long)

    // Quantity below 1 removes the item.
    suspend fun setQuantity(productId: Long, quantity: Int)
    suspend fun remove(productId: Long)
    suspend fun clear()
}
