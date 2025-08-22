package com.example.feature.productdetail.domain

import com.example.core.model.Product
import com.example.feature.productdetail.data.ProductDetailRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductByIdUseCase @Inject constructor(
    private val repository: ProductDetailRepository
) {
    operator fun invoke(id: Int): Flow<Product> = repository.getProduct(id)
}


