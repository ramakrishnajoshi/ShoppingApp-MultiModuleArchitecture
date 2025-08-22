package com.example.core.network.dto

data class ProductDto(
    val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val thumbnail: String?,
    val category: String
)

data class ProductsDto(
    val products: List<ProductDto>
)


