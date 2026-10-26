/*
 * Copyright (c) 2026 Eli Levi. All rights reserved.
 * Developed by Eli Levi - https://www.linkedin.com/in/elilevi/
 */

package com.eli.wittix.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eli.wittix.domain.models.Product
import com.eli.wittix.theme.ProductCatalogTheme
import java.util.Locale

fun Double.asPrice() = "$" + String.format(Locale.US, "%.2f", this)

// Price, plus struck-through original (detail only, per doc) and "-X%" tag when discounted.
// FlowRow + single-line texts: long prices wrap the extras instead of squeezing them.
@Composable
fun PriceRow(product: Product, modifier: Modifier = Modifier, showOriginalPrice: Boolean = true) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
//        verticalAlignment = Alignment.CenterVertically,
        itemVerticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            product.price.asPrice(),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            maxLines = 1,
        )
        if (product.hasDiscount) {
            if (showOriginalPrice) {
                Text(
                    product.originalPrice.asPrice(),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textDecoration = TextDecoration.LineThrough,
                    maxLines = 1,
                )
            }
            Text(
                product.discountLabel,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.errorContainer, RoundedCornerShape(4.dp))
                    .padding(horizontal = 4.dp),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.error,
                maxLines = 1,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PriceRowPreview() = ProductCatalogTheme {
    PriceRow(
        Product(
            1,
            "Charger SXT RWD",
            32999.99, 8.23, 4.1, 5, "vehicle", ""
        )
    )
}
