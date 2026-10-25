package com.eli.wittix.ui.components

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.eli.wittix.theme.ProductCatalogTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.unit.dp

class PriceRowTest {
    @get:Rule val rule = createComposeRule()

    @Test
    fun discountedShowsOriginalAndTag() {
        rule.setContent { ProductCatalogTheme { PriceRow(SampleData.product) } }
        rule.onNodeWithText("$9.99").assertIsDisplayed()
        rule.onNodeWithText("$10.76").assertIsDisplayed()  // 9.99 / (1 - 0.0717)
        rule.onNodeWithText("-7%").assertIsDisplayed()
    }

    @Test
    fun noDiscountShowsPriceOnly() {
        rule.setContent { ProductCatalogTheme { PriceRow(SampleData.outOfStock) } }
        rule.onNodeWithText("$9.99").assertIsDisplayed()
        rule.onNodeWithText("%", substring = true).assertDoesNotExist()
    }

    // Regression: big prices squeezed the "-X%" tag into one char per line.
    @Test
    fun longPriceKeepsEachTextOnOneLine() {
        val car = SampleData.product.copy(price = 32999.99, discountPercentage = 8.23)
        rule.setContent { ProductCatalogTheme { PriceRow(car, Modifier.width(160.dp)) } }
        for (text in listOf("$32999.99", "$35959.45", "-8%")) {
            val node = rule.onNodeWithText(text).assertIsDisplayed().fetchSemanticsNode()
            val layouts = mutableListOf<TextLayoutResult>()
            node.config[SemanticsActions.GetTextLayoutResult].action?.invoke(layouts)
            assertEquals(text, 1, layouts.single().lineCount)
        }
    }
}
