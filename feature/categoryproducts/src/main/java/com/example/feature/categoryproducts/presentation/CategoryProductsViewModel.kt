package com.example.feature.categoryproducts.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.model.Product
import com.example.feature.categoryproducts.domain.GetProductsByCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CategoryProductsUiState(
    val isLoading: Boolean = false,
    val data: List<Product> = emptyList(),
    val error: String? = null,
    val category: String = ""
)

@HiltViewModel
class CategoryProductsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getProducts: GetProductsByCategoryUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(CategoryProductsUiState(isLoading = true))
    val uiState: StateFlow<CategoryProductsUiState> = _uiState.asStateFlow()

    companion object {
        const val ARG_CATEGORY = "category"
        const val RESULT_KEY = "product_result"
    }

    init {
        val category = savedStateHandle.get<String>(ARG_CATEGORY) ?: ""
        _uiState.value = _uiState.value.copy(category = category)
        refresh()
    }

    fun refresh() {
        val category = _uiState.value.category
        if (category.isBlank()) return
        _uiState.value = _uiState.value.copy(isLoading = true, error = null)
        viewModelScope.launch {
            try {
                getProducts(category).collect { list ->
                    _uiState.value = _uiState.value.copy(isLoading = false, data = list)
                }
            } catch (t: Throwable) {
                _uiState.value = _uiState.value.copy(isLoading = false, error = t.message ?: "Unknown error")
            }
        }
    }
}


