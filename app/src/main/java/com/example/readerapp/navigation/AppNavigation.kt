package com.example.readerapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.readerapp.ui.screens.auth.AuthScreen
import com.example.readerapp.ui.screens.home.HomeScreen
import com.example.readerapp.ui.screens.SplashScreen
import com.example.readerapp.ui.screens.detail.DetailScreen
import com.example.readerapp.ui.screens.profile.ProfileScreen
import com.example.readerapp.ui.screens.search.SearchScreen
import com.example.readerapp.ui.screens.update.UpdateScreen

@ExperimentalComposeUiApi
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.SplashScreen.name) {
        composable(AppScreens.SplashScreen.name) {
            SplashScreen(navController = navController)
        }
        composable(AppScreens.AuthScreen.name) {
            AuthScreen(navController = navController)
        }
        composable(AppScreens.HomeScreen.name) {
            HomeScreen(navController = navController)
        }
        composable(AppScreens.SearchScreen.name) {
            SearchScreen(navController = navController)
        }
        val detailScreen = AppScreens.DetailScreen.name
        composable(
            route = "$detailScreen/{bookId}",
            arguments = listOf(navArgument("bookId") { type = NavType.StringType }
            ),
        ) {
            val bookId = it.arguments?.getString("bookId")
            DetailScreen(navController = navController, bookId = bookId!!)
        }
        val updateScreen = AppScreens.UpdateScreen.name
        composable(
            route = "$updateScreen/{bookId}",
            arguments = listOf(navArgument("bookId") { type = NavType.StringType }
            ),
        ) {
            val bookId = it.arguments?.getString("bookId")
            UpdateScreen(navController = navController, bookId = bookId!!)
        }
        composable(AppScreens.ProfileScreen.name) {
            ProfileScreen(navController = navController)
        }
    }
}