package com.example.feature.productdetail.data

import com.example.core.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductDetailRepository {
    fun getProduct(id: Int): Flow<Product>
}


