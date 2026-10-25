package com.eli.wittix.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eli.wittix.domain.models.Category
import com.eli.wittix.theme.ProductCatalogTheme

// First chip is "All" (slug = null).
@Composable
fun CategoryChips(
    categories: List<Category>,
    selected: String?,
    onSelect: (String?) -> Unit,
    modifier: Modifier = Modifier,
) {
    val all = listOf(Category(slug = "", name = "All")) + categories
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(all, key = { it.slug }) { category ->
            val slug = category.slug.ifEmpty { null }
            FilterChip(
                selected = slug == selected,
                onClick = { onSelect(slug) },
                label = { Text(category.name) },
                // M3 default is secondaryContainer; doc wants primaryContainer.
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CategoryChipsPreview() = ProductCatalogTheme {
    CategoryChips(
        listOf(Category("beauty", "Beauty"), Category("furniture", "Furniture")),
        selected = "beauty",
        onSelect = {})
}
