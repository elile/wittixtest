package com.eli.wittix.ui.screens.productlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.eli.wittix.domain.models.Category
import com.eli.wittix.domain.models.Product
import com.eli.wittix.theme.ProductCatalogTheme
import com.eli.wittix.ui.components.CartBadgeIcon
import com.eli.wittix.ui.components.CategoryChips
import com.eli.wittix.ui.components.EmptyState
import com.eli.wittix.ui.components.ErrorState
import com.eli.wittix.ui.components.LoadingState
import com.eli.wittix.ui.components.ProductCard
import com.eli.wittix.ui.components.SearchField
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProductListScreen(
    onProductClick: (Long) -> Unit,
    onCartClick: () -> Unit,
    viewModel: ProductListViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    ProductListContent(
        state = state,
        onQueryChange = viewModel::onQueryChange,
        onCategorySelect = viewModel::onCategorySelect,
        onLoadMore = viewModel::loadMore,
        onRetry = viewModel::retry,
        onProductClick = onProductClick,
        onCartClick = onCartClick,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListContent(
    state: ProductListUiState,
    onQueryChange: (String) -> Unit,
    onCategorySelect: (String?) -> Unit,
    onLoadMore: () -> Unit,
    onRetry: () -> Unit,
    onProductClick: (Long) -> Unit,
    onCartClick: () -> Unit,
    gridState: LazyGridState = rememberLazyGridState(),
) {
    // True when empty too, so a page with no visible matches still fetches the next one.
    val nearEnd by remember {
        derivedStateOf {
            val last = gridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -1
            last >= gridState.layoutInfo.totalItemsCount - 6
        }
    }
    // Re-keyed on load flags so it re-checks after every finished load.
    LaunchedEffect(nearEnd, state.isLoading, state.isLoadingMore, state.products.size) {
        if (nearEnd) onLoadMore()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Product Catalog") },
                actions = { CartBadgeIcon(state.cartCount, onCartClick) },
            )
        },
    ) { padding ->
        Column(Modifier
            .padding(padding)
            .fillMaxSize()) {
            SearchField(
                state.query,
                onQueryChange,
                Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
            CategoryChips(state.categories, state.selectedCategory, onCategorySelect)
            when {
                state.products.isNotEmpty() -> ProductGrid(
                    state,
                    gridState,
                    onProductClick,
                    onRetry
                )

                state.error != null -> ErrorState(state.error, onRetry)
                state.isEmpty -> EmptyState(
                    Icons.Default.Search,
                    "No products found",
                    "Try another search or category."
                )

                else -> LoadingState()
            }
        }
    }
}

@Composable
private fun ProductGrid(
    state: ProductListUiState,
    gridState: LazyGridState,
    onProductClick: (Long) -> Unit,
    onRetry: () -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(160.dp),
        state = gridState,
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(state.products, key = { it.id }) { product ->
            ProductCard(product, onClick = { onProductClick(product.id) })
        }
        // Footer: spinner while paging, or retry when a page failed over cached data.
        if (state.isLoadingMore || state.error != null) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Box(Modifier
                    .fillMaxWidth()
                    .padding(8.dp), contentAlignment = Alignment.Center) {
                    if (state.isLoadingMore) CircularProgressIndicator()
                    else TextButton(onClick = onRetry) { Text("${state.error} · Retry") }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ProductListPreview() = ProductCatalogTheme {
    val product = Product(1, "Essence Mascara Lash Princess", 9.99, 7.17, 4.94, 5, "beauty", "")
    ProductListContent(
        state = ProductListUiState(
            products = List(4) { product.copy(id = it.toLong()) },
            categories = listOf(Category("beauty", "Beauty"), Category("furniture", "Furniture")),
            cartCount = 2,
            isLoadingMore = true,
        ),
        onQueryChange = {},
        onCategorySelect = {},
        onLoadMore = {},
        onRetry = {},
        onProductClick = {},
        onCartClick = {},
    )
}
