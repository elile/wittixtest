package com.eli.wittix.ui.components

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.eli.wittix.theme.WittixTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class StockIndicatorTest {
    @get:Rule val rule = createComposeRule()

    @Test
    fun inStock() {
        rule.setContent { WittixTheme { StockIndicator(5) } }
        rule.onNodeWithText("In stock (5)").assertIsDisplayed()
    }

    @Test
    fun outOfStock() {
        rule.setContent { WittixTheme { StockIndicator(0) } }
        rule.onNodeWithText("Out of stock").assertIsDisplayed()
    }
}
