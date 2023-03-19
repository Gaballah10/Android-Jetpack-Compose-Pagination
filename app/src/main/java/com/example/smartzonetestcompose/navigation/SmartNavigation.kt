package com.example.smartzonetestcompose.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.smartzonetestcompose.ui.details.DetailsScreen
import com.example.smartzonetestcompose.ui.home.HomeScreen
import com.example.smartzonetestcompose.ui.search.SearchScreen
import com.example.smartzonetestcompose.ui.splash.SplashScreen


@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
fun SmartNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = SmartScreens.SplashScreen.name
    ) {

        composable(SmartScreens.SplashScreen.name) {
            SplashScreen(navController = navController)
        }

        composable(SmartScreens.SearchScreen.name) {
            SearchScreen(navController = navController)
        }

        val homeName = SmartScreens.HomeScreen.name
        composable("$homeName/{newsTitle}", arguments = listOf(navArgument("newsTitle") {
            type = NavType.StringType
        })) { backStackEntry ->
            backStackEntry.arguments?.getString("newsTitle").let {

                HomeScreen(navController = navController, newsTitle = it.toString())
            }
        }

        val details = SmartScreens.DetailsScreen.name
        composable(
            "$details/{articleTitle}/{imageUrl}/{publishedAt}/{content}",
            arguments = listOf(navArgument("articleTitle") {
                type = NavType.StringType
            }, navArgument("imageUrl") {
                type = NavType.StringType
            }, navArgument("publishedAt") {
                type = NavType.StringType
            }, navArgument("content") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val articleTitle = backStackEntry.arguments?.getString("articleTitle")
            val imageUrl = backStackEntry.arguments?.getString("imageUrl")
            val publishedAt = backStackEntry.arguments?.getString("publishedAt")
            val content = backStackEntry.arguments?.getString("content")

            DetailsScreen(
                navController = navController,
                articleTitle,
                imageUrl,
                publishedAt,
                content
            )

        }
    }
}