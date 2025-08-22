package com.example.core.network.dto

data class CategoryItemDto(
    val slug: String?,
    val name: String?,
    val url: String?
)

data class DimensionsDto(
    val width: Double?,
    val height: Double?,
    val depth: Double?
)

data class ReviewDto(
    val rating: Int?,
    val comment: String?,
    val date: String?,
    val reviewerName: String?,
    val reviewerEmail: String?
)

data class MetaDto(
    val createdAt: String?,
    val updatedAt: String?,
    val barcode: String?,
    val qrCode: String?
)

data class ProductDto(
    val id: Int?,
    val title: String?,
    val description: String?,
    val category: String?,
    val price: Double?,
    val discountPercentage: Double?,
    val rating: Double?,
    val stock: Int?,
    val tags: List<String>?,
    val brand: String?,
    val sku: String?,
    val weight: Int?,
    val dimensions: DimensionsDto?,
    val warrantyInformation: String?,
    val shippingInformation: String?,
    val availabilityStatus: String?,
    val reviews: List<ReviewDto>?,
    val returnPolicy: String?,
    val minimumOrderQuantity: Int?,
    val meta: MetaDto?,
    val images: List<String>?,
    val thumbnail: String?
)

data class ProductsDto(
    val products: List<ProductDto>?,
    val total: Int? = null,
    val skip: Int? = null,
    val limit: Int? = null
)


