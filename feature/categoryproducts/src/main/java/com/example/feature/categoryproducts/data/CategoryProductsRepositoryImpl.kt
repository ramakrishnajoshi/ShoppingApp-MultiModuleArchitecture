package com.example.feature.categoryproducts.data

import com.example.core.model.Product
import com.example.core.network.api.ProductApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CategoryProductsRepositoryImpl @Inject constructor(
    private val api: ProductApiService
) : CategoryProductsRepository {
    override fun getProducts(category: String): Flow<List<Product>> = flow {
        val products = api.getProductsByCategory(category).products
        emit(products.map { dto ->
            Product(
                id = dto.id,
                title = dto.title,
                description = dto.description,
                price = dto.price,
                thumbnail = dto.thumbnail,
                category = dto.category
            )
        })
    }
}


