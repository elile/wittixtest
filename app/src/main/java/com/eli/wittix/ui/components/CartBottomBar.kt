package com.eli.wittix.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eli.wittix.theme.ProductCatalogTheme

@Composable
fun CartBottomBar(total: Double, onCheckout: () -> Unit, modifier: Modifier = Modifier) {
    Surface(modifier = modifier, shadowElevation = 8.dp) {
        Row(
            Modifier.navigationBarsPadding().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(Modifier.weight(1f)) {
                Text("Total", style = MaterialTheme.typography.labelMedium)
                Text(total.asPrice(), style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            }
            Button(onClick = onCheckout) { Text("Checkout") }
        }
    }
}

@Preview
@Composable
private fun CartBottomBarPreview() = ProductCatalogTheme { CartBottomBar(total = 19.98, onCheckout = {}) }
