package com.eli.wittix.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eli.wittix.domain.models.Product
import com.eli.wittix.theme.WittixTheme
import java.util.Locale

fun Double.asPrice() = "$" + String.format(Locale.US, "%.2f", this)

// Price, plus struck-through original and "-X%" tag when discounted.
@Composable
fun PriceRow(
    product: Product,
    modifier: Modifier = Modifier,
    priceStyle: TextStyle = MaterialTheme.typography.titleMedium,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            product.price.asPrice(),
            style = priceStyle,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
        )
        if (product.hasDiscount) {
            Text(
                product.originalPrice.asPrice(),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textDecoration = TextDecoration.LineThrough,
            )
            Text(
                product.discountLabel,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.errorContainer, RoundedCornerShape(4.dp))
                    .padding(horizontal = 4.dp),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.error,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PriceRowPreview() = WittixTheme { PriceRow(Product(1, "Essence Mascara Lash Princess", 9.99, 7.17, 4.94, 5, "beauty", "")) }
