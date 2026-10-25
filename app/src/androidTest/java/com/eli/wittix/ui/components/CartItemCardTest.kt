package com.eli.wittix.ui.components

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.eli.wittix.theme.ProductCatalogTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class CartItemCardTest {
    @get:Rule val rule = createComposeRule()

    @Test
    fun showsItemAndFiresCallbacks() {
        val events = mutableListOf<String>()
        rule.setContent {
            ProductCatalogTheme {
                CartItemCard(
                    SampleData.cartItem,
                    onDecrease = { events += "-" },
                    onIncrease = { events += "+" },
                    onRemove = { events += "x" },
                )
            }
        }
        rule.onNodeWithText(SampleData.product.title).assertIsDisplayed()
        rule.onNodeWithText("$9.99").assertIsDisplayed()
        rule.onNodeWithText("2").assertIsDisplayed()
        rule.onNodeWithContentDescription("Increase quantity").performClick()
        rule.onNodeWithContentDescription("Decrease quantity").performClick()
        rule.onNodeWithContentDescription("Remove ${SampleData.product.title}").performClick()
        assertEquals(listOf("+", "-", "x"), events)
    }
}
