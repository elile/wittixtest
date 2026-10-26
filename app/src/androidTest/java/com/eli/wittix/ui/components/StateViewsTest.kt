package com.eli.wittix.ui.components

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.eli.wittix.R
import com.eli.wittix.theme.ProductCatalogTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class StateViewsTest {
    @get:Rule val rule = createComposeRule()

    @Test
    fun loadingShowsSpinner() {
        rule.setContent { ProductCatalogTheme { LoadingState() } }
        rule.onNode(hasProgressBarRangeInfo(ProgressBarRangeInfo.Indeterminate)).assertIsDisplayed()
    }

    @Test
    fun emptyShowsTexts() {
        rule.setContent { ProductCatalogTheme { EmptyState(Icons.Default.ShoppingCart, "Your cart is empty", "Add products") } }
        rule.onNodeWithText("Your cart is empty").assertIsDisplayed()
        rule.onNodeWithText("Add products").assertIsDisplayed()
    }

    @Test
    fun errorRetries() {
        var retries = 0
        rule.setContent { ProductCatalogTheme { ErrorState("No internet connection", onRetry = { retries++ }) } }
        rule.onNodeWithText("No internet connection").assertIsDisplayed()
        rule.onNodeWithText("Retry").performClick()
        assertEquals(1, retries)
    }

    @Test
    fun syncStatusShowsBarAndRetryRow() {
        var retries = 0
        rule.setContent {
            ProductCatalogTheme { SyncStatus(syncing = true, error = R.string.error_no_internet, onRetry = { retries++ }) }
        }
        rule.onNode(hasProgressBarRangeInfo(ProgressBarRangeInfo.Indeterminate)).assertIsDisplayed()
        rule.onNodeWithText("No internet connection").assertIsDisplayed()
        rule.onNodeWithText("Retry").performClick()
        assertEquals(1, retries)
    }

    @Test
    fun syncStatusBarStaysAtLeastTwoSeconds() {
        var syncing by mutableStateOf(true)
        rule.mainClock.autoAdvance = false
        rule.setContent { ProductCatalogTheme { SyncStatus(syncing, error = null, onRetry = {}) } }
        val bar = hasProgressBarRangeInfo(ProgressBarRangeInfo.Indeterminate)
        syncing = false
        rule.mainClock.advanceTimeBy(1_000)
        rule.onNode(bar).assertExists()
        rule.mainClock.advanceTimeBy(1_500)
        rule.onNode(bar).assertDoesNotExist()
    }

    @Test
    fun syncStatusHiddenAfterSuccess() {
        rule.setContent { ProductCatalogTheme { SyncStatus(syncing = false, error = null, onRetry = {}) } }
        rule.onNode(hasProgressBarRangeInfo(ProgressBarRangeInfo.Indeterminate)).assertDoesNotExist()
        rule.onNodeWithText("Retry").assertDoesNotExist()
    }
}
