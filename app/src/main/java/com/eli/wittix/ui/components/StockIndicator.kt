package com.eli.wittix.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.eli.wittix.theme.ProductCatalogTheme

@Composable
fun StockIndicator(stock: Int, modifier: Modifier = Modifier) {
    val inStock = stock > 0
    Text(
        if (inStock) "In stock ($stock)" else "Out of stock",
        modifier = modifier,
        style = MaterialTheme.typography.bodyMedium,
        color = if (inStock) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.error,
    )
}

@Preview(showBackground = true)
@Composable
private fun StockIndicatorPreview() = ProductCatalogTheme { StockIndicator(stock = 0) }
