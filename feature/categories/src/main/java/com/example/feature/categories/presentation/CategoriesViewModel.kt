package com.example.feature.categories.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.model.Category
import com.example.core.model.Resource
import com.example.feature.categories.domain.GetCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CategoriesUiState(
    val isLoading: Boolean = false,
    val data: List<Category> = emptyList(),
    val error: String? = null
)

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val getCategories: GetCategoriesUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(CategoriesUiState(isLoading = true))
    val uiState: StateFlow<CategoriesUiState> = _uiState.asStateFlow()

    init { refresh() }

    fun refresh() {
        _uiState.value = CategoriesUiState(isLoading = true)
        viewModelScope.launch {
            try {
                getCategories().collect { list ->
                    _uiState.value = CategoriesUiState(data = list)
                }
            } catch (t: Throwable) {
                _uiState.value = CategoriesUiState(error = t.message ?: "Unknown error")
            }
        }
    }
}


