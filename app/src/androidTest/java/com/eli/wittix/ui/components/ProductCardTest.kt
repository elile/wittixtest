package com.eli.wittix.ui.components

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.eli.wittix.theme.ProductCatalogTheme
import org.junit.Assert.assertEquals
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
        rule.onNodeWithText("4.9").assertIsDisplayed()
        rule.onNodeWithText(SampleData.product.title).performClick()
        assertEquals(1, clicks)
    }
}
