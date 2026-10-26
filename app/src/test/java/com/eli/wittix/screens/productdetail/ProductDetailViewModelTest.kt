/*
 * Copyright (c) 2026 Eli Levi. All rights reserved.
 * Developed by Eli Levi - https://www.linkedin.com/in/elilevi/
 */

package com.eli.wittix.screens.productdetail

import com.eli.wittix.R
import androidx.lifecycle.SavedStateHandle
import com.eli.wittix.domain.ICartRepository
import com.eli.wittix.domain.IProductDetailsRepository
import com.eli.wittix.domain.models.Cart
import com.eli.wittix.domain.models.ProductDetails
import com.eli.wittix.ui.screens.productdetail.ProductDetailViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class ProductDetailViewModelTest {
    private val dispatcher = StandardTestDispatcher()
    private var next: Result<Unit> = Result.failure(IOException())

    private val repo = object : IProductDetailsRepository {
        override fun observeProduct(id: Long): Flow<ProductDetails?> = flowOf(null)
        override suspend fun refresh(id: Long) = next
    }

    private val cartRepo = object : ICartRepository {
        override fun observeCart(): Flow<Cart> = flowOf(Cart(emptyList()))
        override fun observeCount(): Flow<Int> = flowOf(0)
        override suspend fun add(productId: Long) = Unit
        override suspend fun setQuantity(productId: Long, quantity: Int) = Unit
        override suspend fun remove(productId: Long) = Unit
        override suspend fun clear() = Unit
    }

    @Before fun setUp() = Dispatchers.setMain(dispatcher)
    @After fun tearDown() = Dispatchers.resetMain()

    @Test
    fun errorThenRetry() = runTest(dispatcher) {
        val vm = ProductDetailViewModel(repo, cartRepo, SavedStateHandle(mapOf("productId" to 1L)))
        advanceUntilIdle()
        assertEquals(R.string.error_no_internet, vm.state.value.error)
        assertFalse(vm.state.value.isLoading)

        next = Result.failure(IllegalStateException())
        vm.refresh(); advanceUntilIdle()
        assertEquals(R.string.error_generic, vm.state.value.error)

        next = Result.success(Unit)
        vm.refresh(); advanceUntilIdle()
        assertNull(vm.state.value.error)
    }
}
