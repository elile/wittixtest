/*
 * Copyright (c) 2026 Eli Levi. All rights reserved.
 * Developed by Eli Levi - https://www.linkedin.com/in/elilevi/
 */

package com.eli.wittix.ui.screens.productlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eli.wittix.domain.ICartRepository
import com.eli.wittix.domain.IProductsRepository
import com.eli.wittix.ui.screens.userMessage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// Active search + category; category null = "All".
private data class Filters(val query: String = "", val category: String? = null)

// No Dispatchers.IO needed: Retrofit and Room suspend/Flow calls switch threads themselves.
// Only light mapping and state updates run on main.
@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class ProductListViewModel(
    private val repository: IProductsRepository,
    cartRepository: ICartRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(ProductListUiState(isLoading = true))
    val state = _state.asStateFlow()

    private val query = MutableStateFlow("")
    private val category = MutableStateFlow<String?>(null)
    private var filters = Filters()
    private var page = 0
    private var loadJob: Job? = null

    // Typing waits 300ms; clearing the field applies at once.
    private val debouncedFilters = combine(
        query.debounce { if (it.isBlank()) 0L else 300L },
        category,
        ::Filters,
    ).distinctUntilChanged()

    init {
        // Cached categories feed the chips.
        repository.observeCategories()
            .onEach { list -> _state.update { it.copy(categories = list) } }
            .launchIn(viewModelScope)

        // Cart total drives the top bar badge.
        cartRepository.observeCount()
            .onEach { count -> _state.update { it.copy(cartCount = count) } }
            .launchIn(viewModelScope)

        // Grid shows DB rows matching current filters; flatMapLatest drops old query.
        debouncedFilters
            .flatMapLatest { repository.observeProducts(it.query, it.category) }
            .onEach { list -> _state.update { it.copy(products = list) } }
            .launchIn(viewModelScope)

        // New filters refetch page 0 from network.
        debouncedFilters
            .onEach { filters = it; load(0) }
            .launchIn(viewModelScope)

        // Failure is fine: chips fall back to cached categories.
        viewModelScope.launch { repository.refreshCategories() }
    }

    fun onQueryChange(value: String) {
        query.value = value
        _state.update { it.copy(query = value) }
    }

    fun onCategorySelect(slug: String?) {
        category.value = slug
        _state.update { it.copy(selectedCategory = slug) }
    }

    // Blocked after an error so a failing page doesn't retry in a loop.
    fun loadMore() {
        val s = _state.value
        if (s.isLoading || s.isLoadingMore || !s.hasMore || s.error != null) return
        load(page + 1)
    }

    fun retry() = load(page)

    private fun load(page: Int) {
        loadJob?.cancel()
        this.page = page

        loadJob = viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = page == 0,
                    isLoadingMore = page > 0,
                    hasMore = page == 0 || it.hasMore,
                    error = null,
                )
            }
            // One update for result + loading flags, no half-done state.
            val result = repository.loadPage(filters.query, filters.category, page)
            _state.update {
                it.copy(
                    isLoading = false,
                    isLoadingMore = false,
                    hasMore = result.getOrDefault(it.hasMore),
                    error = result.exceptionOrNull()?.userMessage(),
                )
            }
        }
    }
}
