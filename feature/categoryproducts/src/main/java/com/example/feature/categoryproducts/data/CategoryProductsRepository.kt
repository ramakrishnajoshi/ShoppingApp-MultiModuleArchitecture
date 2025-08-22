package com.example.feature.categoryproducts.data

import com.example.core.model.Product
import kotlinx.coroutines.flow.Flow

interface CategoryProductsRepository {
    fun getProducts(category: String): Flow<List<Product>>
}


