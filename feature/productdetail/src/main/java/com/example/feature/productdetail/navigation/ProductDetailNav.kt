package com.example.feature.productdetail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.feature.productdetail.presentation.ProductDetailRoute
import com.example.feature.productdetail.presentation.ProductDetailViewModel

const val ARG_PRODUCT_ID = "id"
const val ROUTE_PRODUCT_DETAIL = "productDetail/{$ARG_PRODUCT_ID}"

fun NavGraphBuilder.productDetailNav(
    navController: NavController
) {
    composable(
        route = ROUTE_PRODUCT_DETAIL,
        arguments = listOf(navArgument(ARG_PRODUCT_ID) { type = NavType.IntType })
    ) {
        ProductDetailRoute(onBackWithResult = { selected ->
            navController.previousBackStackEntry?.savedStateHandle?.set(ProductDetailViewModel.RESULT_SELECTED, selected)
            navController.popBackStack()
        })
    }
}


