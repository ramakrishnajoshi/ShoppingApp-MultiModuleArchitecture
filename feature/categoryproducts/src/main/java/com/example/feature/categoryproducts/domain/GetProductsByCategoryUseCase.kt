package com.example.feature.categoryproducts.domain

import com.example.core.model.Product
import com.example.feature.categoryproducts.data.CategoryProductsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductsByCategoryUseCase @Inject constructor(
    private val repository: CategoryProductsRepository
) {
    operator fun invoke(category: String): Flow<List<Product>> = repository.getProducts(category)
}


