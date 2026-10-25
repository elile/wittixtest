package com.eli.wittix.screens.productlist

import com.eli.wittix.domain.ICartRepository
import com.eli.wittix.domain.IProductsRepository
import com.eli.wittix.domain.models.Cart
import com.eli.wittix.domain.models.Category
import com.eli.wittix.domain.models.Product
import com.eli.wittix.ui.screens.productlist.ProductListViewModel
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
class ProductListViewModelTest {
    private val dispatcher = StandardTestDispatcher()
    private val calls = mutableListOf<String>()
    private var next: Result<Boolean> = Result.success(true)

    private val repo = object : IProductsRepository {
        override fun observeProducts(query: String, category: String?): Flow<List<Product>> = flowOf(emptyList())
        override fun observeCategories(): Flow<List<Category>> = flowOf(emptyList())
        override suspend fun loadPage(query: String, category: String?, page: Int): Result<Boolean> {
            calls += "$query|$category|$page"
            return next
        }
        override suspend fun refreshCategories() = Result.success(Unit)
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
    fun paging() = runTest(dispatcher) {
        val vm = ProductListViewModel(repo, cartRepo)
        advanceUntilIdle()
        vm.loadMore(); advanceUntilIdle()
        vm.onQueryChange("ph"); advanceUntilIdle()   // debounced, resets to page 0
        assertEquals(listOf("|null|0", "|null|1", "ph|null|0"), calls)

        next = Result.failure(IOException())
        vm.loadMore(); advanceUntilIdle()
        assertEquals("No internet connection", vm.state.value.error)
        vm.loadMore(); advanceUntilIdle()              // blocked by error
        assertEquals(4, calls.size)

        next = Result.success(false)
        vm.retry(); advanceUntilIdle()                 // same page again
        assertEquals("ph|null|1", calls.last())
        assertNull(vm.state.value.error)
        assertFalse(vm.state.value.hasMore)
        vm.loadMore(); advanceUntilIdle()              // nothing left
        assertEquals(5, calls.size)

        vm.onCategorySelect("beauty"); advanceUntilIdle()
        assertEquals("ph|beauty|0", calls.last())
    }
}
