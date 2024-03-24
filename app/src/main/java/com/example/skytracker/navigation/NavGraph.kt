package com.example.skytracker.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.skytracker.api.ApiInterface
import com.example.skytracker.screens.SplashScreen
import com.example.skytracker.skytracker.details
import com.example.skytracker.skytracker.searchTracker
import com.example.skytracker.skytracker.searchTrackerViewModel
import com.example.skytracker.skytracker.skyTracker

@Composable
fun SetUpNavGraph(navController: NavHostController,) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(route = Screen.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable(route = Screen.Search.route) {
            searchTracker(navController = navController)
        }
        composable(route = Screen.Sky.route){
            skyTracker(navController = navController)
        }
        composable(
            route = Screen.Details.route,
            arguments = listOf(
                navArgument("id"){
                    type = NavType.StringType
                }
            )
        ){
            details(navController = navController)
        }
    }
}