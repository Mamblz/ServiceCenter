package com.example.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.app.presentation.screens.SignInScreen
import com.example.app.presentation.screens.SplashScreen
import com.example.servicecenter.Presentation.Screen.SignUpScreen
import com.example.servicecenter.Presentation.Screens.MainScreen.MainScreen
//import com.example.servicecenter.Presentation.Screens.ServiceDetailScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavigationRoutes.SplashScreen) {
        composable(NavigationRoutes.SplashScreen) { SplashScreen(navController) }
        composable(NavigationRoutes.SignInScreen) { SignInScreen(navController) }
        composable(NavigationRoutes.SignUpScreen) { SignUpScreen(navController) }
        composable(NavigationRoutes.MainScreen) { MainScreen(navController = navController) }

        composable(
            route = NavigationRoutes.ServiceDetail,
            arguments = listOf(navArgument("serviceId") { type = NavType.StringType })
        ) { backStackEntry ->
            val serviceId = backStackEntry.arguments?.getString("serviceId")
//            ServiceDetailScreen(serviceId = serviceId, navController = navController)
        }
    }
}
