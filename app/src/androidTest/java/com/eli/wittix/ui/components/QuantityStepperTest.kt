package com.eli.wittix.ui.components

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.eli.wittix.theme.ProductCatalogTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class QuantityStepperTest {
    @get:Rule val rule = createComposeRule()

    @Test
    fun buttonsFireCallbacks() {
        val events = mutableListOf<String>()
        rule.setContent {
            ProductCatalogTheme { QuantityStepper(2, onDecrease = { events += "-" }, onIncrease = { events += "+" }) }
        }
        rule.onNodeWithText("2").assertIsDisplayed()
        rule.onNodeWithContentDescription("Increase quantity").performClick()
        rule.onNodeWithContentDescription("Decrease quantity").performClick()
        assertEquals(listOf("+", "-"), events)
    }
}
