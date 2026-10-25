package com.eli.wittix.ui.components

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.eli.wittix.theme.WittixTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class PriceRowTest {
    @get:Rule val rule = createComposeRule()

    @Test
    fun discountedShowsOriginalAndTag() {
        rule.setContent { WittixTheme { PriceRow(SampleData.product) } }
        rule.onNodeWithText("$9.99").assertIsDisplayed()
        rule.onNodeWithText("$10.76").assertIsDisplayed()  // 9.99 / (1 - 0.0717)
        rule.onNodeWithText("-7%").assertIsDisplayed()
    }

    @Test
    fun noDiscountShowsPriceOnly() {
        rule.setContent { WittixTheme { PriceRow(SampleData.outOfStock) } }
        rule.onNodeWithText("$9.99").assertIsDisplayed()
        rule.onNodeWithText("%", substring = true).assertDoesNotExist()
    }
}
