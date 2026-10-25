package com.eli.wittix.ui.components

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.eli.wittix.theme.ProductCatalogTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class CartBadgeIconTest {
    @get:Rule val rule = createComposeRule()

    @Test
    fun showsCountAndClicks() {
        var clicks = 0
        rule.setContent { ProductCatalogTheme { CartBadgeIcon(count = 3, onClick = { clicks++ }) } }
        rule.onNodeWithText("3").assertIsDisplayed()
        rule.onNodeWithContentDescription("Cart").performClick()
        assertEquals(1, clicks)
    }

    @Test
    fun hidesBadgeWhenEmpty() {
        rule.setContent { ProductCatalogTheme { CartBadgeIcon(count = 0, onClick = {}) } }
        rule.onNodeWithText("0").assertDoesNotExist()
    }
}
