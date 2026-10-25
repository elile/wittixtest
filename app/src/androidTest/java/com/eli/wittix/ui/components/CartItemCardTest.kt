package com.eli.wittix.ui.components

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.eli.wittix.theme.ProductCatalogTheme
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
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

    @Test
    fun stepperBesideTitleTrashBesidePrice() {
        rule.setContent { ProductCatalogTheme { CartItemCard(SampleData.cartItem, {}, {}, {}) } }
        fun b(text: String) = rule.onNodeWithText(text, useUnmergedTree = true).getUnclippedBoundsInRoot()
        fun d(desc: String) = rule.onNodeWithContentDescription(desc).getUnclippedBoundsInRoot()
        val title = b(SampleData.product.title)
        val price = b("$9.99")
        val plus = d("Increase quantity")
        val trash = d("Remove ${SampleData.product.title}")
        assertTrue("stepper right of title", plus.left > title.right)
        assertTrue("stepper on title row", plus.top < title.bottom && title.top < plus.bottom)
        assertTrue("price below title", price.top >= title.bottom)
        assertTrue("trash right of price, same row", trash.left > price.right && trash.top < price.bottom && price.top < trash.bottom)
    }
}
