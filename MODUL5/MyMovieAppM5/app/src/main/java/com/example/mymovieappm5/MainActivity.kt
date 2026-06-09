package com.example.mymovieappm5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mymovieappm5.data.local.MovieDatabase
import com.example.mymovieappm5.data.repository.MovieRepository
import com.example.mymovieappm5.domain.model.Movie
import com.example.mymovieappm5.ui.screen.DetailScreen
import com.example.mymovieappm5.ui.screen.HomeScreen
import com.example.mymovieappm5.ui.screen.LanguageScreen
import com.example.mymovieappm5.ui.theme.MyMovieAppM5Theme
import com.example.mymovieappm5.ui.viewmodel.MovieViewModel
import com.example.mymovieappm5.ui.viewmodel.MovieViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = MovieDatabase.getInstance(this)
        val repository = MovieRepository(database)
        val factory = MovieViewModelFactory(repository)

        setContent {
            MyMovieAppM5Theme {
                val navController = rememberNavController()
                val viewModel: MovieViewModel = viewModel(factory = factory)
                var selectedMovie by remember { mutableStateOf<Movie?>(null) }

                NavHost(navController = navController, startDestination = "language") {
                    composable("language") {
                        LanguageScreen(navController = navController)
                    }
                    composable("home") {
                        if (selectedMovie != null) {
                            DetailScreen(
                                movie = selectedMovie!!,
                                onBackClick = { selectedMovie = null }
                            )
                        } else {
                            HomeScreen(
                                viewModel = viewModel,
                                onLanguageClick = {
                                    navController.navigate("language") {
                                        launchSingleTop = true
                                    }
                                },
                                onMovieClick = { movie -> selectedMovie = movie }
                            )
                        }
                    }
                }
            }
        }
    }
}