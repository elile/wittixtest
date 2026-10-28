/*
 * Copyright (c) 2026 Eli Levi. All rights reserved.
 * Developed by Eli Levi - https://www.linkedin.com/in/elilevi/
 */

package com.eli.wittix.data.local.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "products", indices = [Index("category")])
data class ProductEntity(
    @PrimaryKey val id: Long,
    val title: String,
    val description: String,
    val price: Double,
    val discountPercentage: Double,
    val rating: Double,
    val stock: Int,
    val brand: String?,
    val category: String,// category slug
    val thumbnail: String,
    val images: List<String>,
)

// Null category = category row not cached yet.
data class ProductWithCategory(
    @Embedded val product: ProductEntity,
    @Relation(parentColumn = "category", entityColumn = "slug")
    val category: CategoryEntity?,
)
