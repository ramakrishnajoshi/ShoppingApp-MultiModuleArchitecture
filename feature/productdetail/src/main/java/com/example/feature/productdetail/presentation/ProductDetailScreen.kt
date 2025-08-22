package com.example.feature.productdetail.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.ui.components.AppToolbar
import com.example.core.ui.components.ErrorView
import com.example.core.ui.components.LoadingView

@Composable
fun ProductDetailRoute(
    onBackWithResult: (Boolean) -> Unit,
    viewModel: ProductDetailViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    ProductDetailScreen(state = state, onRetry = viewModel::refresh, onBackWithResult = onBackWithResult)
}

@Composable
fun ProductDetailScreen(
    state: ProductDetailUiState,
    onRetry: () -> Unit,
    onBackWithResult: (Boolean) -> Unit
) {
    Scaffold(topBar = { AppToolbar(title = state.data?.title ?: "Product") }) { padding ->
        when {
            state.isLoading -> LoadingView(Modifier.fillMaxSize())
            state.error != null -> ErrorView(state.error, onRetry, Modifier.fillMaxSize())
            else -> Column(modifier = Modifier.fillMaxSize()) {
                Text(text = state.data?.description ?: "")
                Button(onClick = { onBackWithResult(true) }) { Text("Select") }
            }
        }
    }
}


