package com.example.feature.categories.data

import com.example.core.model.Category
import com.example.core.network.api.ProductApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CategoriesRepositoryImpl @Inject constructor(
    private val api: ProductApiService
) : CategoriesRepository {
    override fun getCategories(): Flow<List<Category>> = flow {
        val items = api.getCategories()
        emit(items.map { dto ->
            Category(
                slug = dto.slug ?: "unknown",
                name = dto.name ?: "",
                url = dto.url ?: ""
            )
        })
    }
}


