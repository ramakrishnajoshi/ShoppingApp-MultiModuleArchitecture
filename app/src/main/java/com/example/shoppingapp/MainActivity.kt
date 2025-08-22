package com.example.shoppingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.core.ui.theme.AppTheme
import com.example.feature.categories.navigation.ROUTE_CATEGORIES
import com.example.feature.categories.navigation.categoriesNav
import com.example.feature.categoryproducts.navigation.ROUTE_CATEGORY_PRODUCTS
import com.example.feature.categoryproducts.navigation.categoryProductsNav
import com.example.feature.productdetail.navigation.ROUTE_PRODUCT_DETAIL
import com.example.feature.productdetail.navigation.productDetailNav
import com.example.feature.categoryproducts.presentation.CategoryProductsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                val navController = rememberNavController()
                Surface(color = MaterialTheme.colorScheme.background) {
                    NavHost(navController = navController, startDestination = ROUTE_CATEGORIES) {
                        categoriesNav(onCategoryClick = { category ->
                            navController.navigate("categoryProducts/$category")
                        })
                        categoryProductsNav(onProductClick = { id ->
                            navController.navigate("productDetail/$id")
                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                CategoryProductsViewModel.RESULT_KEY,
                                false
                            )
                        })
                        productDetailNav(navController)
                    }
                }
            }
        }
    }
}