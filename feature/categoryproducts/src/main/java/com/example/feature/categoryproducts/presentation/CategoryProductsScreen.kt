package com.example.feature.categoryproducts.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.ui.components.AppToolbar
import com.example.core.ui.components.ErrorView
import com.example.core.ui.components.LoadingView

@Composable
fun CategoryProductsRoute(
    onProductClick: (Int) -> Unit,
    viewModel: CategoryProductsViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    CategoryProductsScreen(state = state, onRetry = viewModel::refresh, onProductClick = onProductClick)
}

@Composable
fun CategoryProductsScreen(
    state: CategoryProductsUiState,
    onRetry: () -> Unit,
    onProductClick: (Int) -> Unit
) {
    Scaffold(topBar = { AppToolbar(title = state.category) }) { padding ->
        when {
            state.isLoading -> LoadingView(Modifier.fillMaxSize())
            state.error != null -> ErrorView(state.error, onRetry, Modifier.fillMaxSize())
            else -> LazyColumn(contentPadding = padding) {
                items(state.data) { product ->
                    Text(
                        text = product.title,
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable { onProductClick(product.id) }
                    )
                }
            }
        }
    }
}


