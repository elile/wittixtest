package com.eli.wittix.screens.productdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.eli.wittix.domain.ICartRepository
import com.eli.wittix.domain.IProductDetailsRepository
import com.eli.wittix.screens.userMessage
import com.eli.wittix.ui.navigation.ProductDetailRoute
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductDetailViewModel(
    private val repository: IProductDetailsRepository,
    private val cartRepository: ICartRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val productId = savedStateHandle.toRoute<ProductDetailRoute>().productId

    private val _state = MutableStateFlow(ProductDetailUiState(isLoading = true))
    val state = _state.asStateFlow()

    // One-off snackbar messages; not state, so they don't replay on rotation.
    private val _messages = Channel<String>(Channel.BUFFERED)
    val messages = _messages.receiveAsFlow()

    init {
        repository.observeProduct(productId)
            .onEach { p -> _state.update { it.copy(product = p) } }
            .launchIn(viewModelScope)
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            repository.refresh(productId).onFailure { e -> _state.update { it.copy(error = e.userMessage()) } }
            _state.update { it.copy(isLoading = false) }
        }
    }

    fun addToCart() {
        val product = _state.value.product?.product ?: return
        if (!product.inStock) return
        viewModelScope.launch {
            cartRepository.add(productId)
            _messages.send("${product.title} added to cart")
        }
    }
}
