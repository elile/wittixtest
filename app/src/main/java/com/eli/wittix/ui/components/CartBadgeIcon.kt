package com.eli.wittix.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.eli.wittix.theme.ProductCatalogTheme

@Composable
fun CartBadgeIcon(count: Int, onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        BadgedBox(badge = { if (count > 0) Badge { Text("$count") } }) {
            Icon(Icons.Default.ShoppingCart, contentDescription = "Cart")
        }
    }
}

@Preview
@Composable
private fun CartBadgeIconPreview() = ProductCatalogTheme { CartBadgeIcon(count = 3, onClick = {}) }
