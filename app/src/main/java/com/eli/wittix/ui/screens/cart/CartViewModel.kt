package com.eli.wittix.ui.screens.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eli.wittix.domain.ICartRepository
import com.eli.wittix.domain.models.CartItem
import com.eli.wittix.ui.screens.userMessage
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CartViewModel(private val repository: ICartRepository) : ViewModel() {
    val state = repository.observeCart()
        .map { CartUiState(cart = it) }
        .catch { emit(CartUiState(error = it.userMessage())) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            CartUiState(isLoading = true)
        )

    fun increase(item: CartItem) = setQuantity(item, item.quantity + 1)

    // Going below 1 removes the item (handled in the DAO).
    fun decrease(item: CartItem) = setQuantity(item, item.quantity - 1)

    fun remove(item: CartItem) {
        viewModelScope.launch { repository.remove(item.product.id) }
    }

    fun clear() {
        viewModelScope.launch { repository.clear() }
    }

    private fun setQuantity(item: CartItem, quantity: Int) {
        viewModelScope.launch { repository.setQuantity(item.product.id, quantity) }
    }
}
