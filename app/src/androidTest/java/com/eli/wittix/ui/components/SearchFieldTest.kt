package com.eli.wittix.ui.components

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.eli.wittix.theme.ProductCatalogTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class SearchFieldTest {
    @get:Rule val rule = createComposeRule()

    @Test
    fun clearButtonOnlyWhenNotEmpty() {
        var value = "phone"
        rule.setContent { ProductCatalogTheme { SearchField(value = value, onValueChange = { value = it }) } }
        rule.onNodeWithContentDescription("Clear search").performClick()
        assertEquals("", value)
    }

    @Test
    fun noClearButtonWhenEmpty() {
        rule.setContent { ProductCatalogTheme { SearchField(value = "", onValueChange = {}) } }
        rule.onNodeWithContentDescription("Clear search").assertDoesNotExist()
    }
}
