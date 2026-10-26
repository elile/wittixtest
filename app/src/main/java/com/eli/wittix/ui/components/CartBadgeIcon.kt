/*
 * Copyright (c) 2026 Eli Levi. All rights reserved.
 * Developed by Eli Levi - https://www.linkedin.com/in/elilevi/
 */

package com.eli.wittix.ui.components

import androidx.compose.ui.res.stringResource
import com.eli.wittix.R
import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.eli.wittix.theme.ProductCatalogTheme

@Composable
fun CartBadgeIcon(count: Int, onClick: () -> Unit) {
    // BadgedBox outside: IconButton clips content, cutting wide (10+) badges.
    BadgedBox(
        badge = {
            if (count > 0) Badge(Modifier.offset(x = (-12).dp, y = 12.dp)) { Text("$count") }
        },
    ) {
        IconButton(onClick = onClick) {
            Icon(Icons.Default.ShoppingCart, contentDescription = stringResource(R.string.cart))
        }
    }
}

@Preview
@Composable
private fun CartBadgeIconPreview() = ProductCatalogTheme { CartBadgeIcon(count = 12, onClick = {}) }
