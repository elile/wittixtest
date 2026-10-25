package com.eli.wittix.ui.components

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.eli.wittix.theme.WittixTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.semantics.ProgressBarRangeInfo

class StateViewsTest {
    @get:Rule val rule = createComposeRule()

    @Test
    fun loadingShowsSpinner() {
        rule.setContent { WittixTheme { LoadingState() } }
        rule.onNode(hasProgressBarRangeInfo(ProgressBarRangeInfo.Indeterminate)).assertIsDisplayed()
    }

    @Test
    fun emptyShowsTexts() {
        rule.setContent { WittixTheme { EmptyState(Icons.Default.ShoppingCart, "Your cart is empty", "Add products") } }
        rule.onNodeWithText("Your cart is empty").assertIsDisplayed()
        rule.onNodeWithText("Add products").assertIsDisplayed()
    }

    @Test
    fun errorRetries() {
        var retries = 0
        rule.setContent { WittixTheme { ErrorState("No internet connection", onRetry = { retries++ }) } }
        rule.onNodeWithText("No internet connection").assertIsDisplayed()
        rule.onNodeWithText("Retry").performClick()
        assertEquals(1, retries)
    }
}
