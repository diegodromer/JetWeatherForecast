package com.bawp.jetweatherforecast.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bawp.jetweatherforecast.screens.MainScreen
import com.bawp.jetweatherforecast.screens.WeatherSplashScreen
import com.bawp.jetweatherforecast.screens.about.AboutScreen
import com.bawp.jetweatherforecast.screens.favorites.FavoritesScreen
import com.bawp.jetweatherforecast.screens.main.MainViewModel
import com.bawp.jetweatherforecast.screens.search.SearchScreen
import com.bawp.jetweatherforecast.screens.settings.SettingsScreen

@ExperimentalComposeUiApi
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

        val route = WeaterScreens.MainScreen.name
        composable(
            "$route/{city}",
            arguments = listOf(
                navArgument(
                    name = "city") {
                    type = NavType.StringType
                }
            )
        ) { navBack ->
            navBack.arguments?.getString("city").let { city ->

                val mainViewModel = hiltViewModel<MainViewModel>()
                MainScreen(
                    navController = navController,
                    mainViewModel,
                    city = city
                )
            }
        }

        composable(WeaterScreens.SearchScreen.name) {
            SearchScreen(navController = navController)
        }

        composable(WeaterScreens.AboutScreen.name) {
            AboutScreen(navController = navController)
        }

        composable(WeaterScreens.SettingsScreen.name) {
            SettingsScreen(navController = navController)
        }

        composable(WeaterScreens.FavoriteScreen.name) {
            FavoritesScreen(navController = navController)
        }
    }
}