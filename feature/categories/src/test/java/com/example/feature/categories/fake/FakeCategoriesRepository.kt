package com.example.feature.categories.fake

import com.example.core.model.Category
import com.example.feature.categories.data.CategoriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/**
 * Fake implementation of CategoriesRepository for testing purposes.
 * 
 * This can be used in:
 * 1. Unit tests that need a repository without network calls
 * 2. Integration tests across modules
 * 3. UI tests that need predictable data
 * 4. Hilt test modules to replace real repository
 */
class FakeCategoriesRepository : CategoriesRepository {
    
    private var categoriesData: List<Category> = defaultCategories
    
    override fun getCategories(): Flow<List<Category>> = flowOf(categoriesData)
    
    /**
     * Set test data for categories
     */
    fun setCategories(categories: List<Category>) {
        categoriesData = categories
    }
    
    /**
     * Clear all categories (useful for testing empty states)
     */
    fun clearCategories() {
        categoriesData = emptyList()
    }
    
    companion object {
        val defaultCategories = listOf(
            Category(
                slug = "electronics",
                name = "Electronics",
                url = "https://example.com/electronics"
            ),
            Category(
                slug = "books",
                name = "Books", 
                url = "https://example.com/books"
            ),
            Category(
                slug = "clothing",
                name = "Clothing",
                url = "https://example.com/clothing"
            )
        )
    }
}