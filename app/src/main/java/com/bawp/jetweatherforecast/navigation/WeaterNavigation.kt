package com.bawp.jetweatherforecast.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bawp.jetweatherforecast.screens.MainScreen
import com.bawp.jetweatherforecast.screens.WeatherSplashScreen
import com.bawp.jetweatherforecast.screens.main.MainViewModel

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
            val mainViewModel = hiltViewModel<MainViewModel>()
            MainScreen(navController = navController, mainViewModel)
        }
    }
}