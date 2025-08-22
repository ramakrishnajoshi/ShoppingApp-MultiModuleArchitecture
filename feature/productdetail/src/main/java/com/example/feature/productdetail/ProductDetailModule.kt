package com.example.feature.productdetail

import com.example.feature.productdetail.data.ProductDetailRepository
import com.example.feature.productdetail.data.ProductDetailRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ProductDetailModule {
    @Binds
    @Singleton
    abstract fun bindProductDetailRepository(impl: ProductDetailRepositoryImpl): ProductDetailRepository
}


