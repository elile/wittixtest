/*
 * Copyright (c) 2026 Eli Levi. All rights reserved.
 * Developed by Eli Levi - https://www.linkedin.com/in/elilevi/
 */

package com.eli.wittix.ui.components

import androidx.compose.ui.res.stringResource
import com.eli.wittix.R
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.eli.wittix.theme.ProductCatalogTheme

@Composable
fun QuantityStepper(
    quantity: Int,
    onDecrease: () -> Unit,
    onIncrease: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = onDecrease) {
            Icon(Icons.Default.Remove, contentDescription = stringResource(R.string.decrease_quantity))
        }
        Text("$quantity", style = MaterialTheme.typography.titleMedium)
        IconButton(onClick = onIncrease) {
            Icon(Icons.Default.Add, contentDescription = stringResource(R.string.increase_quantity))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun QuantityStepperPreview() =
    ProductCatalogTheme { QuantityStepper(2, onDecrease = {}, onIncrease = {}) }
