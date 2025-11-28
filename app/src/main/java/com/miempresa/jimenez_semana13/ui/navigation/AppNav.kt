package com.miempresa.jimenez_semana13.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.miempresa.jimenez_semana13.auth.AuthViewModel
import com.miempresa.jimenez_semana13.auth.LoginScreen
import com.miempresa.jimenez_semana13.auth.RegisterScreen
import com.miempresa.jimenez_semana13.ui.screens.FormScreen
import com.miempresa.jimenez_semana13.ui.screens.ListScreen
import com.miempresa.jimenez_semana13.ui.screens.DetailsScreen
import com.miempresa.jimenez_semana13.viewmodel.ProductoViewModel

@Composable
fun AppNav(startDestination: String = "login") {

    val navController = rememberNavController()

    // ViewModels
    val authViewModel: AuthViewModel = viewModel()
    val productoViewModel: ProductoViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        // -------------------------------
        // LOGIN
        // -------------------------------
        composable("login") {
            LoginScreen(
                viewModel = authViewModel,
                onLoginSuccess = {
                    navController.navigate("list") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                goToRegister = { navController.navigate("register") }
            )
        }

        // -------------------------------
        // REGISTER
        // -------------------------------
        composable("register") {
            RegisterScreen(
                viewModel = authViewModel,
                onRegisterSuccess = {
                    navController.navigate("list") {
                        popUpTo("register") { inclusive = true }
                    }
                },
                goToLogin = { navController.popBackStack() }
            )
        }

        // -------------------------------
        // PRODUCT LIST
        // -------------------------------
        composable("list") {
            ListScreen(
                viewModel = productoViewModel,
                onAdd = { navController.navigate("form") },
                onEdit = { id -> navController.navigate("form?productId=$id") },
                onDetails = { id -> navController.navigate("details/$id") },
                onLogout = {
                    authViewModel.logout()
                    navController.navigate("login") {
                        popUpTo("list") { inclusive = true }
                    }
                }
            )
        }

        // -------------------------------
        // PRODUCT FORM (CREATE / EDIT)
        // -------------------------------
        composable(
            route = "form?productId={productId}",
            arguments = listOf(
                navArgument("productId") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = ""
                }
            )
        ) { entry ->
            val id = entry.arguments?.getString("productId")
            val realId = if (id.isNullOrBlank()) null else id

            FormScreen(
                viewModel = productoViewModel,
                productId = realId,
                onFinished = { navController.popBackStack() }
            )
        }

        // -------------------------------
        // DETAILS SCREEN
        // -------------------------------
        composable(
            route = "details/{productId}",
            arguments = listOf(
                navArgument("productId") {
                    type = NavType.StringType
                }
            )
        ) { entry ->
            val productId = entry.arguments?.getString("productId")!!

            DetailsScreen(
                viewModel = productoViewModel,
                productId = productId,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
