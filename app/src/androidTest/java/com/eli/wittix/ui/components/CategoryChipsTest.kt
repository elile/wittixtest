package com.eli.wittix.ui.components

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.eli.wittix.theme.WittixTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class CategoryChipsTest {
    @get:Rule val rule = createComposeRule()

    @Test
    fun allFirstAndSelectReturnsSlug() {
        val picked = mutableListOf<String?>()
        rule.setContent {
            WittixTheme { CategoryChips(SampleData.categories, selected = null, onSelect = { picked += it }) }
        }
        rule.onNodeWithText("All").assertIsSelected()
        rule.onNodeWithText("Beauty").performClick()
        rule.onNodeWithText("All").performClick()
        assertEquals(listOf("beauty", null), picked)
    }
}
