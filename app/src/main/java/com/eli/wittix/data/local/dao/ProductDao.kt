/*
 * Copyright (c) 2026 Eli Levi. All rights reserved.
 * Developed by Eli Levi - https://www.linkedin.com/in/elilevi/
 */

package com.eli.wittix.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.eli.wittix.data.local.entities.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    // Null category = all; empty query matches everything, so search works offline too.
    @Query(
        """SELECT * FROM products
        WHERE (:category IS NULL OR category = :category)
        AND (title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%')
        ORDER BY id"""
    )
    fun observeProducts(query: String = "", category: String? = null): Flow<List<ProductEntity>>

    @Query("SELECT * FROM products WHERE id = :id")
    fun observeProduct(id: Long): Flow<ProductEntity?>

    // Upsert, not REPLACE: REPLACE deletes the row and would cascade-wipe cart items.
    @Upsert
    suspend fun upsertAll(products: List<ProductEntity>)
}
