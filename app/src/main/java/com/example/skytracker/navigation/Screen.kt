package com.example.skytracker.navigation

sealed class Screen(val route: String)
{
    data object Splash: Screen(route = "splash_screen")
    data object Search: Screen(route = "search_screen")
    data object Sky: Screen(route = "sky_screen")
    data object Details: Screen(route = "details_screen/{id}"){
        fun getByID(id:String) = "details_screen/$id"
    }
}
