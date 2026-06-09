package com.example.mymovieappm5.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import android.content.Intent
import android.net.Uri
import coil.compose.AsyncImage
import com.example.mymovieappm5.domain.model.Movie
import com.example.mymovieappm5.ui.viewmodel.MovieViewModel
import com.example.mymovieappm5.util.Constants
import com.example.mymovieappm5.util.Resource

val ComicBackground = Color(0xFFF2F0F8)
val ComicSurface = Color(0xFFFFFFFF)
val ComicPurple = Color(0xFF7C3AED)
val ComicPurpleLight = Color(0xFFEDE9FE)
val ComicTextPrimary = Color(0xFF1A1A2E)
val ComicTextSecondary = Color(0xFF6B7280)
val ComicStar = Color(0xFFFBBF24)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: MovieViewModel,
    onLanguageClick: () -> Unit,
    onMovieClick: (Movie) -> Unit
) {
    val popularMovies by viewModel.popularMovies.collectAsState()
    val nowPlayingMovies by viewModel.nowPlayingMovies.collectAsState()
    val searchResults by viewModel.searchResults.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    var isSearching by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ComicBackground)
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Movie App",
                        color = ComicTextPrimary,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(onClick = { onLanguageClick() }) {
                        Icon(
                            Icons.Default.Language,
                            contentDescription = "Language",
                            tint = ComicPurple
                        )
                    }
                }
            }

            if (isSearching) {
                item {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { viewModel.searchMovies(it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        placeholder = { Text("Cari film...", color = ComicTextSecondary) },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = ComicPurple,
                            unfocusedBorderColor = ComicPurpleLight,
                            focusedTextColor = ComicTextPrimary,
                            unfocusedTextColor = ComicTextPrimary,
                            cursorColor = ComicPurple
                        )
                    )
                }
                item {
                    when (searchResults) {
                        is Resource.Loading -> {
                            if (searchQuery.isNotEmpty()) {
                                Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                                    CircularProgressIndicator(color = ComicPurple)
                                }
                            }
                        }
                        is Resource.Success -> {
                            val movies = (searchResults as Resource.Success).data
                            Column {
                                movies.forEach { movie ->
                                    AllMoviesCard(movie = movie, onMovieClick = { onMovieClick(movie) })
                                }
                            }
                        }
                        is Resource.Error -> {
                            Text(
                                text = (searchResults as Resource.Error).message,
                                modifier = Modifier.padding(16.dp),
                                color = Color.Red
                            )
                        }
                    }
                }
            } else {
                item {
                    Text(
                        text = "Featured Movies",
                        color = ComicTextPrimary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                    )
                }
                item {
                    when (nowPlayingMovies) {
                        is Resource.Loading -> {
                            Box(
                                modifier = Modifier.fillMaxWidth().height(260.dp),
                                contentAlignment = Alignment.Center
                            ) { CircularProgressIndicator(color = ComicPurple) }
                        }
                        is Resource.Success -> {
                            val movies = (nowPlayingMovies as Resource.Success).data
                            LazyRow(
                                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                items(movies) { movie ->
                                    FeaturedMovieCard(
                                        movie = movie,
                                        onDetailClick = { onMovieClick(movie) }
                                    )
                                }
                            }
                        }
                        is Resource.Error -> {
                            Text(
                                text = (nowPlayingMovies as Resource.Error).message,
                                modifier = Modifier.padding(16.dp),
                                color = Color.Red
                            )
                        }
                    }
                }

                item {
                    Text(
                        text = "All Movies",
                        color = ComicTextPrimary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 4.dp)
                    )
                }
                when (popularMovies) {
                    is Resource.Loading -> {
                        item {
                            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                                CircularProgressIndicator(color = ComicPurple)
                            }
                        }
                    }
                    is Resource.Success -> {
                        val movies = (popularMovies as Resource.Success).data
                        items(movies) { movie ->
                            AllMoviesCard(
                                movie = movie,
                                onMovieClick = { onMovieClick(movie) }
                            )
                        }
                    }
                    is Resource.Error -> {
                        item {
                            Text(
                                text = (popularMovies as Resource.Error).message,
                                modifier = Modifier.padding(16.dp),
                                color = Color.Red
                            )
                        }
                    }
                }
                item { Spacer(modifier = Modifier.height(24.dp)) }
            }
        }
    }
}

@Composable
fun FeaturedMovieCard(movie: Movie, onDetailClick: () -> Unit) {
    Card(
        modifier = Modifier.width(160.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = ComicSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            AsyncImage(
                model = Constants.IMAGE_BASE_URL + movie.posterPath,
                contentDescription = movie.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)) {
                Text(
                    text = movie.title,
                    color = ComicTextPrimary,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = movie.releaseDate.take(4),
                    color = ComicTextSecondary,
                    fontSize = 11.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
                Button(
                    onClick = onDetailClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(34.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = ComicPurple),
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text("Detail", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun AllMoviesCard(movie: Movie, onMovieClick: () -> Unit) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = ComicSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Row(modifier = Modifier.padding(10.dp)) {
            AsyncImage(
                model = Constants.IMAGE_BASE_URL + movie.posterPath,
                contentDescription = movie.title,
                modifier = Modifier
                    .width(75.dp)
                    .height(105.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = movie.title,
                        color = ComicTextPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = movie.releaseDate.take(4),
                        color = ComicTextSecondary,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = ComicStar,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = String.format("%.1f", movie.voteAverage),
                        color = ComicTextSecondary,
                        fontSize = 12.sp
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = {
                            val url = "https://www.themoviedb.org/movie/${movie.id}"
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            context.startActivity(intent)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = ComicPurple),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 18.dp, vertical = 0.dp),
                        modifier = Modifier.height(32.dp)
                    ) {
                        Text("TMDB", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    }
                    OutlinedButton(
                        onClick = onMovieClick,
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = ComicPurple),
                        border = androidx.compose.foundation.BorderStroke(1.5.dp, ComicPurple),
                        contentPadding = PaddingValues(horizontal = 18.dp, vertical = 0.dp),
                        modifier = Modifier.height(32.dp)
                    ) {
                        Text("Detail", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}