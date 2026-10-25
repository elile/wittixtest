package com.eli.wittix.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import com.eli.wittix.theme.WittixTheme

@Composable
fun QuantityStepper(
    quantity: Int,
    onDecrease: () -> Unit,
    onIncrease: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        // Icons-core has no minus icon; a text glyph matches it.
        IconButton(onClick = onDecrease, modifier = Modifier.semantics { contentDescription = "Decrease quantity" }) {
            Text("−", style = MaterialTheme.typography.titleLarge)
        }
        Text("$quantity", style = MaterialTheme.typography.titleMedium)
        IconButton(onClick = onIncrease) {
            Icon(Icons.Default.Add, contentDescription = "Increase quantity")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun QuantityStepperPreview() = WittixTheme { QuantityStepper(2, onDecrease = {}, onIncrease = {}) }
