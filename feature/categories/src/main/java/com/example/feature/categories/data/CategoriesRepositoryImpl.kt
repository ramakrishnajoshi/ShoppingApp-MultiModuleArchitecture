package com.example.feature.categories.data

import com.example.core.model.Category
import com.example.core.network.di.IoDispatcher
import com.example.core.network.api.ProductApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class CategoriesRepositoryImpl @Inject constructor(
    private val api: ProductApiService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
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
    }.flowOn(ioDispatcher)
}


