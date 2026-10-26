package com.eli.wittix.ui.screens.productlist

import androidx.annotation.StringRes
import com.eli.wittix.domain.models.Category
import com.eli.wittix.domain.models.Product

data class ProductListUiState(
    val products: List<Product> = emptyList(),
    val categories: List<Category> = emptyList(),
    val query: String = "",
    val selectedCategory: String? = null, // null = "All"
    val cartCount: Int = 0,
    val isLoading: Boolean = false, // first page
    val isLoadingMore: Boolean = false, // bottom spinner
    val hasMore: Boolean = true,
    @StringRes val error: Int? = null,
) {
    // Empty only once nothing is left to fetch, else keep paging.
    val isEmpty get() = products.isEmpty() && !hasMore && !isLoading && error == null
}
