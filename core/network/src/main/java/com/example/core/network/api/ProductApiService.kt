package com.example.core.network.api

import com.example.core.network.dto.CategoryItemDto
import com.example.core.network.dto.ProductDto
import com.example.core.network.dto.ProductsDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApiService {
    @GET("products/categories")
    suspend fun getCategories(): List<CategoryItemDto>

    @GET("products/category/{category}")
    suspend fun getProductsByCategory(@Path("category") category: String): ProductsDto

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Int): ProductDto
}


