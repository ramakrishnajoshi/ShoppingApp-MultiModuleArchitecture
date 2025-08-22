package com.example.feature.categories.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.core.ui.components.AppToolbar
import com.example.core.ui.components.ErrorView
import com.example.core.ui.components.LoadingView

@Composable
fun CategoriesRoute(onCategoryClick: (String) -> Unit, viewModel: CategoriesViewModel = hiltViewModel()) {
    val state by viewModel.uiState.collectAsState()
    CategoriesScreen(state = state, onRetry = viewModel::refresh, onCategoryClick = onCategoryClick)
}

@Composable
fun CategoriesScreen(
    state: CategoriesUiState,
    onRetry: () -> Unit,
    onCategoryClick: (String) -> Unit
) {
    Scaffold(topBar = { AppToolbar(title = "Categories") }) { padding ->
        when {
            state.isLoading -> LoadingView(Modifier.fillMaxSize())
            state.error != null -> ErrorView(state.error, onRetry, Modifier.fillMaxSize())
            else -> LazyColumn(contentPadding = padding) {
                items(state.data) { cat ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .padding(12.dp)
                            .fillMaxWidth()
                            .clickable { onCategoryClick(cat.slug) }
                    ) {
                        Text(
                            text = cat.name.ifBlank { cat.slug },
                            modifier = Modifier.padding(16.dp),
                            color = Color(0xFF0D47A1)
                        )
                    }
                }
            }
        }
    }
}


