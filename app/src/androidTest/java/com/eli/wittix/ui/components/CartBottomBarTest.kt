/*
 * Copyright (c) 2026 Eli Levi. All rights reserved.
 * Developed by Eli Levi - https://www.linkedin.com/in/elilevi/
 */

package com.eli.wittix.ui.components

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.eli.wittix.theme.ProductCatalogTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class CartBottomBarTest {
    @get:Rule val rule = createComposeRule()

    @Test
    fun showsTotalAndCheckout() {
        var clicks = 0
        rule.setContent { ProductCatalogTheme { CartBottomBar(total = 19.98, onCheckout = { clicks++ }) } }
        rule.onNodeWithText("$19.98").assertIsDisplayed()
        rule.onNodeWithText("Checkout").performClick()
        assertEquals(1, clicks)
    }
}
