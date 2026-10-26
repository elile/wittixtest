package com.eli.wittix.ui.screens.productdetail

import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.res.stringResource
import com.eli.wittix.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Shop
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.eli.wittix.domain.models.Product
import com.eli.wittix.domain.models.ProductDetails
import com.eli.wittix.theme.ProductCatalogTheme
import com.eli.wittix.ui.components.AddToCartButton
import com.eli.wittix.ui.components.CategoryChip
import com.eli.wittix.ui.components.ErrorState
import com.eli.wittix.ui.components.LoadingState
import com.eli.wittix.ui.components.PriceRow
import com.eli.wittix.ui.components.ProductImage
import com.eli.wittix.ui.components.RatingRow
import com.eli.wittix.ui.components.sharedProductImage
import com.eli.wittix.ui.components.StockIndicator
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProductDetailScreen(onBack: () -> Unit, viewModel: ProductDetailViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val resources = LocalResources.current
    LaunchedEffect(Unit) {
        viewModel.addedToCart.collect {
            snackbarHostState.showSnackbar(resources.getString(R.string.added_to_cart, it))
        }
    }
    ProductDetailContent(
        state = state,
        onBack = onBack,
        onAddToCart = viewModel::addToCart,
        onRetry = viewModel::refresh,
        snackbarHostState = snackbarHostState,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailContent(
    state: ProductDetailUiState,
    onBack: () -> Unit,
    onAddToCart: () -> Unit,
    onRetry: () -> Unit,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
) {
    val details = state.product
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        details?.product?.title.orEmpty(),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.back))
                    }
                },
            )
        },
        bottomBar = {
            if (details != null) {
                AddToCartButton(
                    enabled = details.product.inStock,
                    onClick = onAddToCart,
                    modifier = Modifier
                        .navigationBarsPadding()
                        .padding(16.dp),
                )
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { padding ->
        val modifier = Modifier.padding(padding)
        when {
            // Cached product wins over a failed refresh (offline first).
            details != null -> DetailsBody(details, modifier)
            state.error != null -> ErrorState(stringResource(state.error), onRetry, modifier)
            else -> LoadingState(modifier)
        }
    }
}

@Composable
private fun DetailsBody(details: ProductDetails, modifier: Modifier) {
    val product = details.product
    Column(modifier.verticalScroll(rememberScrollState())) {
        ProductImage(
            details.heroImage,
            contentDescription = product.title,
            modifier = Modifier
                .sharedProductImage(product.id)
                .fillMaxWidth()
                .height(300.dp),
        )
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            CategoryChip(name = product.category, selected = true, onClick = {})
            Text(
                product.title,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                RatingRow(product.rating)
                StockIndicator(product.stock)
            }

            PriceRow(product)
            details.brand?.let { Text(stringResource(R.string.brand, it), style = MaterialTheme.typography.bodyMedium) }
            HorizontalDivider(Modifier.padding(vertical = 8.dp))
            Text(
                stringResource(R.string.description),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(details.description, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Preview
@Composable
private fun ProductDetailPreview() = ProductCatalogTheme {
    ProductDetailContent(
        state = ProductDetailUiState(
            product = ProductDetails(
                product = Product(
                    1,
                    "Essence Mascara Lash Princess",
                    9.99,
                    7.17,
                    4.94,
                    5,
                    "beauty",
                    ""
                ),
                description = "A popular mascara known for its volumizing and lengthening effects.",
                brand = "Essence",
                images = emptyList(),
            ),
        ),
        onBack = {},
        onAddToCart = {},
        onRetry = {},
    )
}
