package com.eli.wittix.ui.components

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.eli.wittix.theme.ProductCatalogTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class AddToCartButtonTest {
    @get:Rule val rule = createComposeRule()

    @Test
    fun enabledClicks() {
        var clicks = 0
        rule.setContent { ProductCatalogTheme { AddToCartButton(enabled = true, onClick = { clicks++ }) } }
        rule.onNodeWithText("Add to Cart").performClick()
        assertEquals(1, clicks)
    }

    @Test
    fun disabledWhenOutOfStock() {
        rule.setContent { ProductCatalogTheme { AddToCartButton(enabled = false, onClick = {}) } }
        rule.onNodeWithText("Add to Cart").assertIsNotEnabled()
    }
}
