package com.example.mycomicappcompose4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComicApp()
        }
    }
}

@Composable
fun ComicApp() {
    val navController = rememberNavController()
    val factory = ComicViewModelFactory(appName = "MyComicAppCompose4")
    val viewModel: ComicViewModel = viewModel(factory = factory)

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            ComicListScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
        composable(
            route = "detail/{comicId}",
            arguments = listOf(navArgument("comicId") { type = NavType.IntType })
        ) { backStackEntry ->
            val comicId = backStackEntry.arguments?.getInt("comicId")
            ComicDetailScreen(
                comicId = comicId,
                viewModel = viewModel
            )
        }
        composable("language") {
            LanguageScreen(navController = navController)
        }
    }
}