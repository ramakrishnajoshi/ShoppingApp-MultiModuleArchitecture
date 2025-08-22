package com.example.feature.categoryproducts.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0)),
                        modifier = Modifier
                            .padding(12.dp)
                            .fillMaxWidth()
                            .clickable { onProductClick(product.id) }
                    ) {
                        AsyncImage(
                            model = product.thumbnail,
                            contentDescription = product.title,
                        )
                        Text(
                            text = product.title,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                            color = Color(0xFFE65100)
                        )
                        Text(
                            text = "${'$'}${product.price}  •  ⭐ ${product.rating}",
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 0.dp),
                            color = Color(0xFFBF360C)
                        )
                    }
                }
            }
        }
    }
}


