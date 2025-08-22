package com.example.feature.categories.data

import com.example.core.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoriesRepository {
    fun getCategories(): Flow<List<Category>>
}


