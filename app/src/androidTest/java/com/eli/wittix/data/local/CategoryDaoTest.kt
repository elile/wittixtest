/*
 * Copyright (c) 2026 Eli Levi. All rights reserved.
 * Developed by Eli Levi - https://www.linkedin.com/in/elilevi/
 */

package com.eli.wittix.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.eli.wittix.data.local.entities.CategoryEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CategoryDaoTest {
    private val dao = Room.inMemoryDatabaseBuilder(
        ApplicationProvider.getApplicationContext(), AppDatabase::class.java
    ).build().categoryDao()

    @Test
    fun replaceAll_dropsMissing_updatesAndAddsRest() = runBlocking {
        dao.upsertAll(listOf(CategoryEntity("beauty", "Beauty"), CategoryEntity("old", "Old")))

        dao.replaceAll(listOf(CategoryEntity("beauty", "Beauty 2"), CategoryEntity("new", "New")))

        assertEquals(
            listOf(CategoryEntity("beauty", "Beauty 2"), CategoryEntity("new", "New")),
            dao.observeCategories().first(),
        )
    }
}
