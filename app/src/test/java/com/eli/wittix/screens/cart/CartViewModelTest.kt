/*
 * Copyright (c) 2026 Eli Levi. All rights reserved.
 * Developed by Eli Levi - https://www.linkedin.com/in/elilevi/
 */

package com.eli.wittix.screens.cart

import com.eli.wittix.R
import com.eli.wittix.domain.ICartRepository
import com.eli.wittix.domain.models.Cart
import com.eli.wittix.ui.screens.cart.CartViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CartViewModelTest {
    private val dispatcher = StandardTestDispatcher()

    private val repo = object : ICartRepository {
        override fun observeCart(): Flow<Cart> = flow { throw IllegalStateException("db") }
        override fun observeCount(): Flow<Int> = flow { emit(0) }
        override suspend fun add(productId: Long) = Unit
        override suspend fun setQuantity(productId: Long, quantity: Int) = Unit
        override suspend fun remove(productId: Long) = Unit
        override suspend fun clear() = Unit
    }

    @Before fun setUp() = Dispatchers.setMain(dispatcher)
    @After fun tearDown() = Dispatchers.resetMain()

    @Test
    fun dbErrorShowsMessage() = runTest(dispatcher) {
        val vm = CartViewModel(repo)
        backgroundScope.launch { vm.state.collect {} }   // WhileSubscribed needs a collector
        advanceUntilIdle()
        assertEquals(R.string.error_generic, vm.state.value.error)
        assertFalse(vm.state.value.isLoading)
    }
}
