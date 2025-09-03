package com.example.feature.categories.data

import com.example.core.model.Category
import com.example.core.network.api.ProductApiService
import com.example.core.network.dto.CategoryDto
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

class CategoriesRepositoryImplTest {

    private lateinit var mockApiService: ProductApiService
    private lateinit var repository: CategoriesRepositoryImpl

    @Before
    fun setup() {
        mockApiService = mockk()
        repository = CategoriesRepositoryImpl(mockApiService)
    }

    @Test
    fun `getCategories should map DTOs to domain models correctly`() = runTest {
        // Given
        val mockDtos = listOf(
            CategoryDto(slug = "electronics", name = "Electronics", url = "https://example.com/electronics"),
            CategoryDto(slug = "books", name = "Books", url = "https://example.com/books")
        )
        
        coEvery { mockApiService.getCategories() } returns mockDtos

        // When
        val result = repository.getCategories().first()

        // Then
        assertEquals(2, result.size)
        assertEquals("electronics", result[0].slug)
        assertEquals("Electronics", result[0].name)
        assertEquals("https://example.com/electronics", result[0].url)
        assertEquals("books", result[1].slug)
        assertEquals("Books", result[1].name)
        assertEquals("https://example.com/books", result[1].url)
    }

    @Test
    fun `getCategories should handle null values gracefully`() = runTest {
        // Given
        val mockDtos = listOf(
            CategoryDto(slug = null, name = null, url = null)
        )
        
        coEvery { mockApiService.getCategories() } returns mockDtos

        // When
        val result = repository.getCategories().first()

        // Then
        assertEquals(1, result.size)
        assertEquals("unknown", result[0].slug)
        assertEquals("", result[0].name)
        assertEquals("", result[0].url)
    }

    @Test
    fun `getCategories should emit empty list when API returns empty list`() = runTest {
        // Given
        coEvery { mockApiService.getCategories() } returns emptyList()

        // When
        val result = repository.getCategories().first()

        // Then
        assertTrue(result.isEmpty())
    }
}