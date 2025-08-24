package com.example.feature.productdetail.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.model.Product
import com.example.feature.productdetail.domain.GetProductByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProductDetailUiState(
    val isLoading: Boolean = false,
    val data: Product? = null,
    val error: String? = null
)

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getProductById: GetProductByIdUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProductDetailUiState(isLoading = true))
    val uiState: StateFlow<ProductDetailUiState> = _uiState.asStateFlow()

    companion object {
        const val ARG_PRODUCT_ID = "id"
        const val RESULT_SELECTED = "result_selected"
    }

    init { refresh() }

    fun refresh() {
        val id = savedStateHandle.get<Int>(ARG_PRODUCT_ID) ?: return
        _uiState.value = ProductDetailUiState(isLoading = true)
        viewModelScope.launch {
            try {
                getProductById(id).collect { product ->
                    _uiState.value = ProductDetailUiState(data = product)
                }
            } catch (t: Throwable) {
                _uiState.value = ProductDetailUiState(error = t.message ?: "Unknown error")
            }
        }
    }
}


