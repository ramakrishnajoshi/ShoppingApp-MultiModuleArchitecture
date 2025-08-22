package com.example.feature.categories.domain

import com.example.core.model.Category
import com.example.feature.categories.data.CategoriesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val repository: CategoriesRepository
) {
    operator fun invoke(): Flow<List<Category>> = repository.getCategories()
}


