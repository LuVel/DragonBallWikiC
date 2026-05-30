package com.example.dragonballwikic.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dragonballwikic.ui.auth.LoginScreen
import com.example.dragonballwikic.ui.auth.RegisterScreen
import com.example.dragonballwikic.ui.search.SearchScreen

// Rutas de navegación
object Routes {
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val SEARCH = "search"
}

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        // ── LOGIN ──────────────────────────────────────────────────────
        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Routes.SEARCH) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Routes.REGISTER)
                }
            )
        }

        // ── REGISTER ───────────────────────────────────────────────────
        composable(Routes.REGISTER) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(Routes.SEARCH) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.popBackStack()
                }
            )
        }

        // ── SEARCH ─────────────────────────────────────────────────────
        composable(Routes.SEARCH) {
            SearchScreen(
                onLogout = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.SEARCH) { inclusive = true }
                    }
                }
            )
        }
    }
}