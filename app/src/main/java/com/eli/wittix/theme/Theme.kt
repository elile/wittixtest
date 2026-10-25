package com.eli.wittix.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun ProductCatalogTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = ProductCatalogColors, content = content)
}
