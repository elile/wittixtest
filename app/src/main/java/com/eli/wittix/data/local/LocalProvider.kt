/*
 * Copyright (c) 2026 Eli Levi. All rights reserved.
 * Developed by Eli Levi - https://www.linkedin.com/in/elilevi/
 */

package com.eli.wittix.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.eli.wittix.data.local.dao.CartDao
import com.eli.wittix.data.local.dao.CategoryDao
import com.eli.wittix.data.local.dao.ProductDao
import com.eli.wittix.data.local.entities.CartItemEntity
import com.eli.wittix.data.local.entities.CategoryEntity
import com.eli.wittix.data.local.entities.ProductEntity
import kotlinx.serialization.json.Json

@Database(
    entities = [ProductEntity::class, CategoryEntity::class, CartItemEntity::class],
    version = 1,
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun categoryDao(): CategoryDao
    abstract fun cartDao(): CartDao
}

// SQLite can't store lists; saves ProductEntity.images as a JSON string.
class Converters {
    @TypeConverter
    fun fromList(value: List<String>): String = Json.encodeToString(value)

    @TypeConverter
    fun toList(value: String): List<String> = Json.decodeFromString(value)
}

object LocalProvider {
    fun database(context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "wittix.db").build()
}
