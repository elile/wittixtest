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

class RatingRowTest {
    @get:Rule val rule = createComposeRule()

    @Test
    fun roundsToOneDecimal() {
        rule.setContent { ProductCatalogTheme { RatingRow(4.94) } }
        rule.onNodeWithText("4.9").assertIsDisplayed()
        rule.onNodeWithContentDescription("Rating").assertIsDisplayed()
    }
}
