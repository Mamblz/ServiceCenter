package com.example.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.app.presentation.screens.MainScreen
import com.example.app.presentation.screens.SignInScreen
import com.example.app.presentation.screens.SplashScreen
import com.example.servicecenter.Presentation.Screen.SignUpScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavigationRoutes.SplashScreen) {
        composable(NavigationRoutes.SplashScreen) { SplashScreen(navController) }
        composable(NavigationRoutes.SignInScreen) { SignInScreen(navController) }
        composable(NavigationRoutes.SignUpScreen) { SignUpScreen(navController) }
        composable(NavigationRoutes.MainScreen) { MainScreen(navController = navController)
        }
    }
}
