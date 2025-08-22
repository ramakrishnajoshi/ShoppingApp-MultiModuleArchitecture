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
        val products = api.getProductsByCategory(category).products ?: emptyList()
        emit(products.map { dto ->
            Product(
                id = dto.id ?: -1,
                title = dto.title ?: "",
                description = dto.description ?: "",
                price = dto.price ?: 0.0,
                discountPercentage = dto.discountPercentage ?: 0.0,
                rating = dto.rating ?: 0.0,
                stock = dto.stock ?: 0,
                brand = dto.brand ?: "",
                category = dto.category ?: "",
                thumbnail = dto.thumbnail ?: "",
                images = dto.images ?: emptyList()
            )
        })
    }
}


