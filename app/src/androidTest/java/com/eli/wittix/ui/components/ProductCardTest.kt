/*
 * Copyright (c) 2026 Eli Levi. All rights reserved.
 * Developed by Eli Levi - https://www.linkedin.com/in/elilevi/
 */

package com.eli.wittix.ui.components

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.eli.wittix.theme.ProductCatalogTheme
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class ProductCardTest {
    @get:Rule val rule = createComposeRule()

    @Test
    fun showsProductAndClicks() {
        var clicks = 0
        rule.setContent { ProductCatalogTheme { ProductCard(SampleData.product, onClick = { clicks++ }) } }
        rule.onNodeWithText(SampleData.product.title).assertIsDisplayed()
        rule.onNodeWithText("$9.99").assertIsDisplayed()
        rule.onNodeWithText("-7%").assertIsDisplayed()
        rule.onNodeWithText("$10.76").assertDoesNotExist()  // original price is detail-only
        rule.onNodeWithText("4.9").assertIsDisplayed()
        rule.onNodeWithText(SampleData.product.title).performClick()
        assertEquals(1, clicks)
    }

    @Test
    fun ratingSitsAtEndOfPriceLine() {
        rule.setContent {
            ProductCatalogTheme { ProductCard(SampleData.product, onClick = {}, Modifier.width(180.dp)) }
        }
        // Card merges its children's semantics; unmerged tree gives each text's own bounds.
        val tag = rule.onNodeWithText("-7%", useUnmergedTree = true).getUnclippedBoundsInRoot()
        val rating = rule.onNodeWithText("4.9", useUnmergedTree = true).getUnclippedBoundsInRoot()
        assertTrue("rating right of tag", rating.left > tag.right)
        assertTrue("same line", rating.top < tag.bottom && tag.top < rating.bottom)
    }
}
