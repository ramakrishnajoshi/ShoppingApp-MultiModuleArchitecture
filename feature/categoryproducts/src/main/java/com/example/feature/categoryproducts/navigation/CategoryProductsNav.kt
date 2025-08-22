package com.example.feature.categoryproducts.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.feature.categoryproducts.presentation.CategoryProductsRoute
import com.example.feature.categoryproducts.presentation.CategoryProductsViewModel

const val ARG_CATEGORY = "category"
const val ROUTE_CATEGORY_PRODUCTS = "categoryProducts/{$ARG_CATEGORY}"

fun NavGraphBuilder.categoryProductsNav(onProductClick: (Int) -> Unit) {
    composable(
        route = ROUTE_CATEGORY_PRODUCTS,
        arguments = listOf(navArgument(ARG_CATEGORY) { type = NavType.StringType })
    ) {
        CategoryProductsRoute(onProductClick = onProductClick)
    }
}


