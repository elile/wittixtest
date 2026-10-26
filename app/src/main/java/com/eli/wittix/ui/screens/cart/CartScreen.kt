package com.eli.wittix.ui.screens.cart

import androidx.compose.ui.res.stringResource
import com.eli.wittix.R
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.eli.wittix.domain.models.Cart
import com.eli.wittix.domain.models.CartItem
import com.eli.wittix.domain.models.Product
import com.eli.wittix.theme.ProductCatalogTheme
import com.eli.wittix.ui.components.CartBottomBar
import com.eli.wittix.ui.components.CartItemCard
import com.eli.wittix.ui.components.EmptyState
import com.eli.wittix.ui.components.LoadingState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CartScreen(onBack: () -> Unit, viewModel: CartViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val comingSoon = stringResource(R.string.checkout_coming_soon)
    CartContent(
        state = state,
        onBack = onBack,
        onIncrease = viewModel::increase,
        onDecrease = viewModel::decrease,
        onRemove = viewModel::remove,
        onClear = viewModel::clear,
        // Checkout is out of scope for the assignment.
        onCheckout = { Toast.makeText(context, comingSoon, Toast.LENGTH_SHORT).show() },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartContent(
    state: CartUiState,
    onBack: () -> Unit,
    onIncrease: (CartItem) -> Unit,
    onDecrease: (CartItem) -> Unit,
    onRemove: (CartItem) -> Unit,
    onClear: () -> Unit,
    onCheckout: () -> Unit,
) {
    val items = state.cart.items
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.cart_title)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.back))
                    }
                },
                actions = {
                    if (items.isNotEmpty()) {
                        IconButton(onClick = onClear) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = stringResource(R.string.clear_cart)
                            )
                        }
                    }
                },
            )
        },
        bottomBar = { if (items.isNotEmpty()) CartBottomBar(state.cart.total, onCheckout) },
    ) { padding ->
        val modifier = Modifier.padding(padding)
        when {
            state.isLoading -> LoadingState(modifier)
            state.error != null -> EmptyState(
                Icons.Default.Warning,
                stringResource(R.string.cart_load_error),
                stringResource(state.error),
                modifier
            )

            items.isEmpty() -> EmptyState(
                Icons.Default.ShoppingCart,
                stringResource(R.string.cart_empty_title),
                stringResource(R.string.cart_empty_message),
                modifier,
            )

            else -> LazyColumn(
                modifier,
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(items, key = { it.product.id }) { item ->
                    CartItemCard(
                        item,
                        onDecrease = { onDecrease(item) },
                        onIncrease = { onIncrease(item) },
                        onRemove = { onRemove(item) },
                        // Fades removed items out and slides the rest up.
                        modifier = Modifier.animateItem(),
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun CartPreview() = ProductCatalogTheme {
    val product = Product(1, "Essence Mascara Lash Princess", 9.99, 7.17, 4.94, 5, "beauty", "")
    CartContent(
        state = CartUiState(Cart(listOf(CartItem(product, 2), CartItem(product.copy(id = 2), 1)))),
        onBack = {},
        onIncrease = {},
        onDecrease = {},
        onRemove = {},
        onClear = {},
        onCheckout = {},
    )
}
