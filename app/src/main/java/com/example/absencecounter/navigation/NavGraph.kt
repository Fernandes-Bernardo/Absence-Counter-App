package com.example.absencecounter.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.absencecounter.ui.screens.HomeScreen
import com.example.absencecounter.ui.screens.HomeViewModel

sealed class Screen(val route: String) {
    data object Home : Screen("home")
}

@Composable
fun NavGraph(
    navController: NavHostController,
    homeViewModel: HomeViewModel
) {

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {

        composable(Screen.Home.route) {
//            HomeScreen(
//                viewModel = homeViewModel
//            )
        }
    }
}
