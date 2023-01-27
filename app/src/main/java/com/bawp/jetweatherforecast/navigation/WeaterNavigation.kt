package com.bawp.jetweatherforecast.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bawp.jetweatherforecast.screens.MainScreen
import com.bawp.jetweatherforecast.screens.WeatherSplashScreen

@Composable
fun WeaterNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = WeaterScreens.SplashScreen.name
    ) {
        composable(WeaterScreens.SplashScreen.name) {
            WeatherSplashScreen(navController = navController)
        }
        composable(WeaterScreens.MainScreen.name) {
            MainScreen(navController = navController)
        }
    }
}