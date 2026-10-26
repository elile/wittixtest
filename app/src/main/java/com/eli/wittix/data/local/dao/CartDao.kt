/*
 * Copyright (c) 2026 Eli Levi. All rights reserved.
 * Developed by Eli Levi - https://www.linkedin.com/in/elilevi/
 */

package com.eli.wittix.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.eli.wittix.data.local.entities.CartItemEntity
import com.eli.wittix.data.local.entities.CartItemWithProduct
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Transaction
    @Query("SELECT * FROM cart_items ORDER BY rowid")
    fun observeCart(): Flow<List<CartItemWithProduct>>

    @Query("SELECT COALESCE(SUM(quantity), 0) FROM cart_items")
    fun observeCount(): Flow<Int>

    @Query("SELECT quantity FROM cart_items WHERE productId = :productId")
    suspend fun quantity(productId: Long): Int?

    @Upsert
    suspend fun upsert(item: CartItemEntity)

    @Query("DELETE FROM cart_items WHERE productId = :productId")
    suspend fun remove(productId: Long)

    @Query("DELETE FROM cart_items")
    suspend fun clear()

    @Transaction
    suspend fun add(productId: Long) =
        upsert(CartItemEntity(productId, (quantity(productId) ?: 0) + 1))

    // Quantity below 1 removes the item, per spec.
    @Transaction
    suspend fun setQuantity(productId: Long, quantity: Int) =
        if (quantity < 1) remove(productId) else upsert(CartItemEntity(productId, quantity))
}
