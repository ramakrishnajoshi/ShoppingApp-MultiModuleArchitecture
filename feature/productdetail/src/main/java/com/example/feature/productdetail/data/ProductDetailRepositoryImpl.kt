package com.example.feature.productdetail.data

import com.example.core.model.Product
import com.example.core.network.api.ProductApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductDetailRepositoryImpl @Inject constructor(
    private val api: ProductApiService
) : ProductDetailRepository {
    override fun getProduct(id: Int): Flow<Product> = flow {
        val dto = api.getProductById(id)
        emit(
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
        )
    }
}


