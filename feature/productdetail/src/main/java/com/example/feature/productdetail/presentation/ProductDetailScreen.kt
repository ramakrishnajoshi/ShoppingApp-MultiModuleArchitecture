package com.example.feature.productdetail.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
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
            else -> Column(modifier = Modifier.fillMaxSize().padding(12.dp)) {
                Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)), modifier = Modifier.fillMaxWidth()) {
                    AsyncImage(
                        model = state.data?.images?.firstOrNull() ?: state.data?.thumbnail,
                        contentDescription = state.data?.title ?: ""
                    )
                    Text(text = state.data?.title ?: "", modifier = Modifier.padding(12.dp), color = Color(0xFF1B5E20))
                    Text(text = "Brand: ${state.data?.brand ?: ""}", modifier = Modifier.padding(horizontal = 12.dp))
                    Text(text = "Price: ${'$'}${state.data?.price ?: 0.0} • Stock: ${state.data?.stock ?: 0}", modifier = Modifier.padding(horizontal = 12.dp))
                    Text(text = "Discount: ${state.data?.discountPercentage ?: 0.0}% • Rating: ${state.data?.rating ?: 0.0}", modifier = Modifier.padding(12.dp))
                }
                Text(text = state.data?.description ?: "", modifier = Modifier.padding(12.dp))
                Button(onClick = { onBackWithResult(true) }, modifier = Modifier.padding(12.dp)) { Text("Select") }
            }
        }
    }
}


