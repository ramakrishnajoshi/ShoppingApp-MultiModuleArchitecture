package com.example.feature.categoryproducts

import com.example.feature.categoryproducts.data.CategoryProductsRepository
import com.example.feature.categoryproducts.data.CategoryProductsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CategoryProductsModule {
    @Binds
    @Singleton
    abstract fun bindCategoryProductsRepository(impl: CategoryProductsRepositoryImpl): CategoryProductsRepository
}


