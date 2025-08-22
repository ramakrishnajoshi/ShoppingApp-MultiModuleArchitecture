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
                id = dto.id,
                title = dto.title,
                description = dto.description,
                price = dto.price,
                thumbnail = dto.thumbnail,
                category = dto.category
            )
        )
    }
}


