/*
 * Copyright (c) 2026 Eli Levi. All rights reserved.
 * Developed by Eli Levi - https://www.linkedin.com/in/elilevi/
 */

package com.eli.wittix.ui.components

import androidx.compose.ui.res.stringResource
import com.eli.wittix.R
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
        if (inStock) stringResource(R.string.in_stock, stock) else stringResource(R.string.out_of_stock),
        modifier = modifier,
        style = MaterialTheme.typography.bodyMedium,
        color = if (inStock) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
    )
}

@Preview(showBackground = true)
@Composable
private fun StockIndicatorPreview() = ProductCatalogTheme { StockIndicator(stock = 0) }
