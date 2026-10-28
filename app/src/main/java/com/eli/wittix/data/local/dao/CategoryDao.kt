/*
 * Copyright (c) 2026 Eli Levi. All rights reserved.
 * Developed by Eli Levi - https://www.linkedin.com/in/elilevi/
 */

package com.eli.wittix.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.eli.wittix.data.local.entities.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories ORDER BY name")
    fun observeCategories(): Flow<List<CategoryEntity>>

    @Upsert
    suspend fun upsertAll(categories: List<CategoryEntity>)

    @Query("DELETE FROM categories WHERE slug NOT IN (:slugs)")
    suspend fun deleteAllExcept(slugs: List<String>)

    // Server sends the full list, so rows missing from it were deleted there.
    @Transaction
    suspend fun replaceAll(categories: List<CategoryEntity>) {
        deleteAllExcept(categories.map { it.slug })
        upsertAll(categories)
    }
}
