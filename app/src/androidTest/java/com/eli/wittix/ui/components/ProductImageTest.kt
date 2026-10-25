package com.eli.wittix.ui.components

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.eli.wittix.theme.ProductCatalogTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class ProductImageTest {
    @get:Rule val rule = createComposeRule()

    @Test
    fun exposesDescription() {
        rule.setContent { ProductCatalogTheme { ProductImage("", contentDescription = "Mascara") } }
        rule.onNodeWithContentDescription("Mascara").assertExists()
    }
}
